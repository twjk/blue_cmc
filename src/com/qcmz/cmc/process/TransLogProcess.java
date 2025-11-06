package com.qcmz.cmc.process;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.ITransLogService;
import com.qcmz.cmc.vo.TransLogIdRange;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：翻译日志处理
 * 修改历史：
 */
@Component
public class TransLogProcess {
	@Autowired
	private ITransLogService transLogService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 翻译日志迁移
	 * 从数据导出到excel，并删除数据库数据 
	 * 修改历史：
	 */
	public void migrate(){
		try {
			Date yesterday = DateUtil.addDay(new Date(), -1);
			Date start = DateUtil.setMinTime(yesterday);
			Date end = DateUtil.setMaxTime(yesterday);
			
			String dateStr = DateUtil.formatDate2(yesterday);
			logger.info("开始导出翻译日志："+dateStr);
			
			TransLogIdRange idRange = transLogService.getIdRange(start, end);
			if(idRange.getMinLogId()==null 
					|| idRange.getMinLogId().equals(idRange.getMaxLogId())){
				logger.info("没有翻译日志："+dateStr);
				return;
			}
			Long lastId = idRange.getMinLogId()-1;
			Long maxId = idRange.getMaxLogId();
			
			TransLogExport transLogExport = new TransLogExport(transLogService, getFilePath(dateStr));
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("maxId", maxId);
			cond.put("lastId", lastId);
			transLogExport.export(cond);
	
			logger.info("开始清除翻译日志："+dateStr);
			transLogService.deleteLog(idRange.getMinLogId(), idRange.getMaxLogId());
			
		} catch (Exception e) {
			logger.error("导出翻译日志失败", e);
		}
		logger.info("导出翻译日志完成");
	}
	
	/**
	 * 将翻译日志文件上传到OSS 
	 * 修改历史：
	 */
	public void uploadOSS(){
		logger.info("开始将翻译日志上传到OSS");
		String dirPath = getDirPath();
		File dir = new File(dirPath);
		if(dir.exists()){
			File[] files = dir.listFiles();
			for (File file : files) {
				try {
					String url = FileServiceUtil.saveOrUploadFile(file, "translog");
					if(StringUtil.notBlankAndNull(url)){
						FileUtil.deleteQuietly(file.getPath());
					}
				} catch (Exception e) {
					logger.error("翻译日志上传到OSS失败："+file.getPath(), e);
				}
			}
		}
		logger.info("完成将翻译日志上传到OSS");
	}
	
	/**
	 * 获取翻译日志目录
	 * @return
	 * 修改历史：
	 */
	private String getDirPath(){
		return new StringBuilder()
				.append(FilePathUtil.getAbsolutePath("files"))
				.append("/translog/")
				.toString()
				;
	}
	
	/**
	 * 获取翻译日志路径
	 * @param date
	 * @return
	 * 修改历史：
	 */
	private String getFilePath(String date){
		String dirPath = getDirPath();
		FileUtil.newDir(dirPath);
		
		return new StringBuilder().append(dirPath).append(date).append(".xlsx").toString();
	}
}
