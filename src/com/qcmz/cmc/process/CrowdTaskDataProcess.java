package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtLib;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.file.comparator.FileNameAscComparator;
import com.qcmz.framework.file.filter.FileNameFilterByEndsWith;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class CrowdTaskDataProcess {
	@Autowired
	private ICrowdTaskLibService crowdTaskLibService;
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 导入题库
	 * @param params
	 */
	public void importLib(Map<String, String> params){
		String dirPath = params.get("dirPath");
		String baseLibName = params.get("libName");
		
		logger.info("开始导入题库："+dirPath);
		
		File dir = new File(dirPath);
		if(!dir.exists()) throw new ParamException("目录不存在");
		
		//遍历json文件
		ListIterator<Object> it;
		List<CmcCtSubject> subjectList;
		CmcCtLib lib;
		CmcCtSubject subject;
		CmcCtOption option;
		String libName, fileName, seq;
		Long libId, subjectCount;
		
		List<File> jsonFiles = Arrays.asList(dir.listFiles(new FileNameFilterByEndsWith("json")));
		Collections.sort(jsonFiles, new FileNameAscComparator());
		for (File jsonFile : jsonFiles) {
			fileName = jsonFile.getName().substring(0,jsonFile.getName().lastIndexOf("."));
			seq = fileName.substring(fileName.lastIndexOf(".")+1);
			libName = new StringBuilder(baseLibName).append("_").append(seq).toString();
			
			logger.info("开始导入题库："+jsonFile.getName()+"-->"+libName);
			
			
			//创建库
			lib = crowdTaskLibService.getLibByName(libName);
			if(lib==null){
				lib = crowdTaskLibService.saveLib(libName);
			}
			libId = lib.getLibid();
			
			subjectCount = crowdTaskLibService.getSubjectCount(libId);
			if(subjectCount>0){
				logger.error("题库下题目不为空："+libId+","+libName);
				continue;
			}
			
			subjectList = new ArrayList<CmcCtSubject>();
			
			it = JSON.parseArray(FileUtil.fileToString(jsonFile)).listIterator();
			while(it.hasNext()){
				Object obj = it.next();
				
				//题目
				subject = new CmcCtSubject();
				subject.setLibid(libId);
				subject.setSubjectcat(DictConstant.DICT_CROWDTASK_SUBJECTCAT_TXTMARK);
				subject.setSubjecttype(DictConstant.DICT_CROWDTASK_SUBJECTTYPE_RADIO);
				subject.setPassscore(0);
				subject.setSubjectreward(0.02);
				subject.setContenttype(DictConstant.DICT_CROWDTASK_CONTENTTYPE_TXT);
				subject.setContent(obj.toString());
				subject.setRecordtimes(1);
				subject.setStatus(SystemConstants.STATUS_ON);
				
				//选项
				for (int i = 1; i < 6; i++) {
					option = new CmcCtOption();
					option.setLibid(libId);
					option.setCmcCtSubject(subject);
					option.setOptionvalue(String.valueOf(i));
					option.setOptiontitle(option.getOptionvalue());
					option.setWritemore(SystemConstants.STATUS_OFF);
					subject.getOptions().add(option);
				}
				
				subjectList.add(subject);
			}
			
			crowdTaskLibService.saveOrUpdateAll(subjectList);
			
			logger.info("完成导入题库："+jsonFile.getName()+"-->"+libName+"，题目数"+subjectList.size());
		}
		
		logger.info("完成导入题库："+dirPath);
	}
	
	/**
	 * 定期删除用户任务文件
	 */
	public void deleteUserCrowdTaskFile(){
		Date date = DateUtil.addDay(DateUtil.getSysDateOnly(), -50);
		deleteUserCrowdTaskFile(date);
	}
	
	/**
	 * 删除指定任务的用户任务文件
	 * @param taskId
	 */
	public void deleteUserCrowdTaskFile(Long taskId){
		String taskDirPath = UserUtil.getCrowdTaskPath(taskId);
		boolean delete = FileServiceUtil.deleteDir(taskDirPath);
		String log = new StringBuilder("众包任务【").append(taskId).append("】删除用户文件【").append(taskDirPath).append("】：").append(delete).toString();
		logger.info(log);
	}
	
	/**
	 * 删除用户任务的文件
	 * @param params
	 */
	public void deleteUserCrowdTaskFile(Map<String, String> params){
		Date minEndDate = DateUtil.parseDate(params.get("start"));
		Date maxEndDate = DateUtil.parseDate(params.get("end"));
		Date date = minEndDate;
		while(date.getTime()<=maxEndDate.getTime()){
			deleteUserCrowdTaskFile(date);
			date = DateUtil.addDay(date, 1);
		}
	}
	
	/**
	 * 删除已完结/已取消的用户任务文件（已完结/已取消、自动取消、公司任务、完成/取消日期）
	 * @param date
	 */
	public void deleteUserCrowdTaskFile(Date date){
		String dateStr = DateUtil.formatDate2(date);
		long startTime = System.currentTimeMillis();
		String hintTitle = new StringBuilder("删除【").append(dateStr).append("】已完成或已取消的用户任务文件，").toString(); 
		logger.info(hintTitle+"已开始");
		
		int pageSize = 100;
		int successCount = 0;
		int failCount = 0;
		Long lastAnswerId = null;
		List<CmcCtUseranswer> answers = null;
		String fileUrl = null;
		String filePath = null;
		String dirPath = null;
		
		Set<String> deletedDirs = new HashSet<String>();
		
		do{
			//同一用户任务文件可能会在不同文件夹下，所以以答案为单位进行处理
			answers = userCrowdTaskService.findUserAnswer4DeleteFile(date, pageSize, lastAnswerId);
			if(answers.isEmpty()) break;
			for (CmcCtUseranswer answer : answers) {
				lastAnswerId = answer.getUaid();
				
				//https://zc.testflights.cn:8443/files/cmc/crowdtask/3100/20240922/20/11036101/240922200906435123/1727008790571647060.wav
				if(StringUtil.notBlankAndNull(answer.getVoice())){
					fileUrl = answer.getVoice();
				}else if(StringUtil.notBlankAndNull(answer.getPic())){
					fileUrl = answer.getPic();
				}else{
					continue;
				}
				
				filePath = FileServiceUtil.url2FilePath(fileUrl);
				dirPath = FilePathUtil.getParentOfDir(FilePathUtil.getParent(filePath));	///crowdtask/3100/20240922/20/11036101
				if(!deletedDirs.contains(dirPath)){
					if(FileServiceUtil.deleteDir(dirPath)){
						successCount++;
					}else{
						failCount++;
					}
				}else{
					successCount++;
				}
			}
			logger.info(new StringBuilder(hintTitle).append("处理中，成功【").append(successCount).append("】失败【").append(failCount).append("】lastAnswerId【").append(lastAnswerId).append("】").toString());
		}while(true);
		
		String mail = new StringBuilder(hintTitle).append("已完成，成功【").append(successCount).append("】失败【").append(failCount).append("】耗时【").append((System.currentTimeMillis()-startTime)/1000).append("秒】").toString();
		logger.info(mail);
		
		MailUtil.sendSimpleMail(SystemConfig.MAILS, mail, mail);
		
	}
}
