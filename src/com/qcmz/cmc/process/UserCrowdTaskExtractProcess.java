package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtSimilar;
import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.service.IUserCrowdTaskSimilarService;
import com.qcmz.cmc.util.CrowdTaskUtil;
import com.qcmz.cmc.vo.UserCrowdTaskHcicloudBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.geography.GeographyUtil;
import com.qcmz.framework.media.AudioUtil;
import com.qcmz.framework.media.vo.AudioBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 用户任务数据提取
 */
@Component
public class UserCrowdTaskExtractProcess {
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private IUserCrowdTaskSimilarService userCrowdTaskSimilarService;
	
	private Logger logger = Logger.getLogger(this.getClass()); 
	
	/**
	 * 提取
	 * @param params
	 */
	public void extract(Map<String, String> params){
		String baseDir = params.get("baseDir");
		if(StringUtil.isBlankOrNull(baseDir) 
				|| !FileUtil.newDir(baseDir).exists()){
			throw new ParamException("存储路径有误");
		}
		
		List<Long> taskIds = StringUtil.split2LongList(params.get("taskIds"), ",");
		if(taskIds.isEmpty()){
			throw new ParamException("任务编号有误");
		}
		
		Date minFinishTime = DateUtil.setMinTime(DateUtil.parseDate(params.get("start")));
		Date maxFinishTime = DateUtil.setMaxTime(DateUtil.parseDate(params.get("end")));
		
		String lastId = params.get("lastId");	
		for (Long taskId : taskIds) {
			extractAudio(taskId, baseDir, minFinishTime, maxFinishTime, lastId);
//			deleteErrorFormat(new File(baseDir), baseDir);
			lastId = null;
		}
	}
	
	/**
	 * 提取音频采集任务
	 * 录音格式:16k16bit单通道
	 * 1.先下载到指定目录，如果文件已存在则继续；
	 * 2.检测语音文件格式，合格的拷贝到ok路径
	 * 3.保存lastId
	 * 4.检查音频格式
	 * 每条wav录音对应一个同名的Tag文件记录录音信息。Tag文件录音人ID编号，文本内容，录音者性别，年龄，省份，录音设备，录音环境（客厅，办公室等等）
	 * 每个录音人的每条语句分配记录一个唯一的句编号
	 * 数字串和文本串录音，按录音人分开保存
	 */
	public void extractAudio(Long taskId, String absBaseDirPath, Date minFinishTime, Date maxFinishTime, String lastId){
		List<CmcCtUsertask> userTasks = null;
		List<String> userTaskIds = null;
		//<subjectId, CmcCtUseranswer>
		Map<Long, CmcCtUseranswer> bestUserAnswerMap;
		List<CmcCtUseranswer> userAnswers;
		CmcCtUseranswer bestUserAnswer;
		UserCrowdTaskHcicloudBean bean;
		
		String realAbsBaseDirPath = null;
		String absDirPath;
		String onlyFileName = null;
		String filePath;
		String jsonFilePath;
		File file;
		String voiceUrl;
		
		int pageSize = 10;
		int seq = 0;
		int repeat = 0;
		
		String uuidRepeatDirPath = new StringBuilder(absBaseDirPath).append("uuidrepeat").toString();
		
		String title = new StringBuilder("任务【").append(taskId).append("】，存储路径【").append(absBaseDirPath).append("】").toString();
		logger.info(title+"开始");
		
		//1.下载并生成json文件		
		do{
			userTasks = userCrowdTaskService.findUserTaskByTaskId(taskId, DictConstant.DICT_USERCROWDTASK_STATUS_FINISH, minFinishTime, maxFinishTime, pageSize, lastId);
			if(userTasks.isEmpty()) break;
			
			for (CmcCtUsertask utask : userTasks) {
				lastId = utask.getUtid();
				
				//uuid重复的放入备库
				userTaskIds = userCrowdTaskService.findUserTaskByUuid(taskId, utask.getUuid(), DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
				if(userTaskIds.size()>1 && !userTaskIds.get(0).equals(utask.getUtid())){
					repeat++;
					realAbsBaseDirPath = uuidRepeatDirPath;
				}else{
					realAbsBaseDirPath = absBaseDirPath;
				}
				
				//最佳答案
				bestUserAnswerMap = new HashMap<Long, CmcCtUseranswer>();
				userAnswers = userCrowdTaskService.findUserAnswerJoinSubject(utask.getUtid(), utask.getTaskid());
				for (CmcCtUseranswer userAnswer : userAnswers) {
					bestUserAnswer = bestUserAnswerMap.get(userAnswer.getSubjectid());
					if(bestUserAnswer==null || userAnswer.getScore()>bestUserAnswer.getScore()){
						bestUserAnswerMap.put(userAnswer.getSubjectid(), userAnswer);
					}
				}
				//提取
				for (Long subjectId : bestUserAnswerMap.keySet()) {
					bestUserAnswer = bestUserAnswerMap.get(subjectId);
					absDirPath = getAbsDirPath(realAbsBaseDirPath, utask.getUserid(), bestUserAnswer.getUaid());
					
					//下载音频文件
					voiceUrl = convertDomain(bestUserAnswer.getVoice());
					
					onlyFileName = String.valueOf(bestUserAnswer.getUaid());
					filePath = getFilePath(absDirPath, onlyFileName+"."+FileTypeUtil.getFileType(voiceUrl));
					file = new File(filePath);
					if(!file.exists()){
						file = HttpUtil.download(voiceUrl, absDirPath+"/", onlyFileName);
					}
					
					//创建json文件
					jsonFilePath = getJsonFilePath(absDirPath, onlyFileName);
					if(!new File(jsonFilePath).exists()){
						bean = new UserCrowdTaskHcicloudBean();
						bean.setId(bestUserAnswer.getUaid());
						bean.setUserId(utask.getUserid());
						bean.setContent(bestUserAnswer.getCmcCtSubject().getContent());
						bean.setCountryName(utask.getCountryname());
						bean.setCityName(utask.getCityname());
						FileUtil.stringToFile(JsonUtil.object2String(bean), jsonFilePath);
					}
					
					//检查文件格式，异常通知
					if(!checkAudioFormat(file)){
						MailUtil.sendSimpleMail(SystemConfig.MAILS, null, "文件格式异常", file.getPath());
					}
				}
				
				seq++;
				logger.info(title+" 已处理："+seq+"，uuid重复数："+repeat+"，最后用户任务编号："+lastId);
			}
		}while(true);
		logger.info(title+"下载完成");
		
		//2.记录lastId
		saveLastId(DateUtil.formatDateTime(new Date())+","+taskId+","+lastId, absBaseDirPath);		
		
		//3.检查音频格式
//		logger.info(title+"开始检查音频格式");
//		checkAudioFormat(new File(absOkBaseDirPath), absOkBaseDirPath);
		
		logger.info(title+"已完成");
	}
	
	/**
	 * 检查音频格式
	 * @param file
	 * @param absDirPath
	 */
	public void checkAudioFormat(File file, String absDirPath){
		if(file.isFile()){
			String fileType = FileTypeUtil.getFileType(file.getPath());
			if(!"wav".equals(fileType)) return;
			
			AudioBean audioBean = AudioUtil.getAudioInfo(file.getPath());
			if(!CrowdTaskUtil.checkAudioFormat(audioBean)){
				Long uaid = Long.valueOf(FilePathUtil.getOnlyFileName(file.getPath()));
				CmcCtUseranswer userAnswer = userCrowdTaskService.getUserAnswerJoin(uaid);
				String log = new StringBuilder()
						.append(audioBean.getChannels())
						.append(",").append(audioBean.getSampleRate())
						.append(",").append(audioBean.getSampleSizeInBits())
						.append(",").append(userAnswer.getCmcCtUsertask().getPlatform())
						.append(",").append(userAnswer.getCmcCtUsertask().getVersion())
						.append(",").append(userAnswer.getCmcCtUsertask().getUserid())
						.append(",").append(userAnswer.getCmcCtUsertask().getUtid())
						.append(",").append(userAnswer.getVoice())
						.toString();
				saveCheckAudioLog(log, absDirPath);				
				System.out.println(log);
			}
		}else{
			File[] files = file.listFiles();
			for (File f : files) {
				checkAudioFormat(f, absDirPath);
			}
		}
	}
	
	/**
	 * 遍历格式有误的用户数据
	 * @param file
	 */
	public void deleteErrorFormat(File file, String absDirPath){
		if(!file.exists()) return;
		if(file.isDirectory()){
			File[] files = file.listFiles();
			File audioFile = null;
			String fileType = null;
			File parentDir = null;
			File grantfatherDir = null;
			boolean deleteDir = false;
			for (File f : files) {
				if(f.isDirectory()) break;
				fileType = FileTypeUtil.getFileType(f.getPath());
				if("wav".equals(fileType)) {
					audioFile = f;
				}
			}
			if(audioFile!=null){
				AudioBean audioBean = AudioUtil.getAudioInfo(audioFile.getPath());
				if(audioBean.getChannels()!=1
						|| audioBean.getSampleRate()!=16000
						|| audioBean.getSampleSizeInBits()!=16){
					//直接删除用户目录
					parentDir = audioFile.getParentFile();
					grantfatherDir = parentDir.getParentFile();
					deleteDir = FileUtil.deleteDir(parentDir);
					if(!deleteDir){
						saveDeleteLog(grantfatherDir.getPath(), absDirPath);
					}
					if(grantfatherDir.listFiles().length==0){
						FileUtil.deleteDir(grantfatherDir);
					}
				}
			}else{
				for (File f : files) {
					deleteErrorFormat(f, absDirPath);
				}
			}
		}
	}
	
	/**
	 * 保存文件删除失败日志
	 * @param log
	 * @param absDirPath
	 */
	public void saveDeleteLog(String log, String absDirPath){
		try {
			String logFilePath = new StringBuilder(absDirPath).append(FilePathUtil.FILE_SEP).append("deleteFail.txt").toString();
			FileUtils.writeStringToFile(new File(logFilePath), log+"\r\n", true);
		} catch (Exception e) {
			logger.error("保存日志失败");
		}
	}
	
	/**
	 * 保存音频格式检查日志
	 * @param log
	 */
	public void saveCheckAudioLog(String log, String absDirPath){
		try {
			String logFilePath = new StringBuilder(absDirPath).append(FilePathUtil.FILE_SEP).append("checkAudio.txt").toString();
			FileUtils.writeStringToFile(new File(logFilePath), log+"\r\n", true);
		} catch (Exception e) {
			logger.error("保存音频格式检查日志失败");
		}
	}
	
	/**
	 * 保存lastId
	 * @param log
	 * @param absDirPath
	 */
	public void saveLastId(String log, String absDirPath){
		try {
			String filePath = new StringBuilder(absDirPath).append("/lastId.txt").toString();
			FileUtils.writeStringToFile(new File(filePath), log+"\r\n", true);
		} catch (Exception e) {
			logger.error("保存lastId失败");
		}
	}
	
	/**
	 * 检查音频格式是否是16k16bit单声道
	 * 16k16bit单声道
	 * @param file
	 * @return
	 */
	private boolean checkAudioFormat(File file){
		return CrowdTaskUtil.checkAudioFormat(AudioUtil.getAudioInfo(file.getPath()));
	}
	
	/**
	 * 获取目录路径
	 * @param absBaseDirPath
	 * @param userId
	 * @param uaId
	 * @return
	 */
	private String getAbsDirPath(String absBaseDirPath, Long userId, Long uaId){
		StringBuilder sb = new StringBuilder(absBaseDirPath).append(FilePathUtil.FILE_SEP).append(userId);
		if(uaId!=null){
			sb.append(FilePathUtil.FILE_SEP).append(uaId);
		}
		return sb.toString();
	}
	
	/**
	 * 获取文件路径
	 * @param absDirPath
	 * @param fileName
	 * @return
	 */
	private String getFilePath(String absDirPath, String fileName){
		return new StringBuilder(absDirPath).append(FilePathUtil.FILE_SEP).append(fileName).toString();
	}
	
	/**
	 * 获取json文件路径
	 * @param absDirPath
	 * @param onlyFileName
	 * @return
	 */
	private String getJsonFilePath(String absDirPath, String onlyFileName){
		return new StringBuilder(absDirPath).append(FilePathUtil.FILE_SEP).append(onlyFileName).append(".json").toString();
	}
	
	/**
	 * 转为真实域名，速度快，还省流量
	 * @param url
	 * @return
	 */
	private String convertDomain(String url){
		return url.replace("https://f4.qcmuzhi.com", "https://voicetrans.oss-cn-beijing.aliyuncs.com");
	}
	
	/**
	 * 匹配相似
	 */
	public void matchSimilar(){
		List<CmcCtUsertask> userTasks = new ArrayList<CmcCtUsertask>();
		CmcCtUsertask ut = null;
		CmcCtUsertask ut2 = null;
		CmcCtUseranswer answer = null;
		Integer same = null;
		int seq = 0;
		
		Set<Long> numTaskIds = new HashSet<Long>();
		numTaskIds.add(14L);
		numTaskIds.add(15L);
		numTaskIds.add(36L);
		
		Set<Long> txtTaskIds = new HashSet<Long>();
		txtTaskIds.add(6L);
		txtTaskIds.add(30L);
		
		List<CmcCtSimilar> list = userCrowdTaskSimilarService.findSimilar(null);
		for (CmcCtSimilar cmcCtSimilar : list) {
			//获取并设置用户任务编号
			userTasks.clear();
			if(cmcCtSimilar.getUaid()!=null){
				answer = userCrowdTaskService.getUserAnswerJoin(cmcCtSimilar.getUaid());
				ut = answer.getCmcCtUsertask();
				cmcCtSimilar.setUtid(answer.getUtid());
			}else{
				if(DictConstant.DICT_CROWDTASK_CONTENTTYPE_NUM.equals(cmcCtSimilar.getContenttype())){
					userTasks = userCrowdTaskService.findUserTask(cmcCtSimilar.getUserid(), numTaskIds);
				}else if(DictConstant.DICT_CROWDTASK_CONTENTTYPE_TXT.equals(cmcCtSimilar.getContenttype())){
					userTasks = userCrowdTaskService.findUserTask(cmcCtSimilar.getUserid(), txtTaskIds);
				}
				for (CmcCtUsertask userTask : userTasks) {
					if(DictConstant.DICT_USERCROWDTASK_STATUS_FINISH.equals(userTask.getStatus())){
						cmcCtSimilar.setUtid(userTask.getUtid());
						ut = userTask;
						break;
					}
				}
			}
			
			//获取并设置相似用户任务编号
			userTasks.clear();
			if(DictConstant.DICT_CROWDTASK_CONTENTTYPE_NUM.equals(cmcCtSimilar.getContenttype())){
				
				userTasks = userCrowdTaskService.findUserTask(cmcCtSimilar.getSimilaruserid(), numTaskIds);
			}else if(DictConstant.DICT_CROWDTASK_CONTENTTYPE_TXT.equals(cmcCtSimilar.getContenttype())){
				userTasks = userCrowdTaskService.findUserTask(cmcCtSimilar.getSimilaruserid(), txtTaskIds);
			}
			for (CmcCtUsertask userTask : userTasks) {
				if(DictConstant.DICT_USERCROWDTASK_STATUS_FINISH.equals(userTask.getStatus())){
					cmcCtSimilar.setSimilarutid(userTask.getUtid());
					ut2 = userTask;
					break;
				}
			}
			
			//比较
			same = null;
			if(ut.getUuid().equals(ut2.getUuid())){
				same = 1;
			}else if(StringUtil.notBlankAndNull(ut.getCountryname()) && StringUtil.notBlankAndNull(ut2.getCountryname())
					&& StringUtil.notBlankAndNull(ut.getCityname()) && StringUtil.notBlankAndNull(ut2.getCityname())
					&& StringUtil.notBlankAndNull(ut.getLon()) && StringUtil.notBlankAndNull(ut2.getLon())){
				if(!ut.getCountryname().equals(ut2.getCountryname())){
					same = 0;
				}else if(!ut.getCityname().equals(ut2.getCityname())){
					same = 0;
				}else if(GeographyUtil.distance(ut.getLat(), ut.getLon(), ut2.getLat(), ut2.getLon())>1000){
					same = 0;
				}else{
					same = 1;
				}
			}
			cmcCtSimilar.setSameuser(same);
			
			userCrowdTaskSimilarService.updateSimilar(cmcCtSimilar);
			
			seq++;
			if(seq%100==0){
				logger.info("已处理记录数："+seq);
			}
		}
		logger.info("完成处理，合计："+seq);
	}
	
	public void test(){
		Long count = null;
		Integer contentType = 2;
		int num = 0;
		int seq = 0;
		List<CmcCtSimilar> list = userCrowdTaskSimilarService.findSimilar(contentType);
		for (CmcCtSimilar cmcCtSimilar : list) {
			count = userCrowdTaskSimilarService.getCountBySimilarUserId(cmcCtSimilar.getUserid(), contentType);
			if(count>0){
				num++;
			}
			seq++;
			if(seq%100==0){
				System.out.println("已处理记录数："+seq);
			}
		}
		System.out.println("完成处理,结果："+num);
	}
	
}
