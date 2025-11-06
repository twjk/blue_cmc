package com.qcmz.cmc.job;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJob;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileUtil;

/**
 * 类说明：清理临时文件任务
 * 修改历史：
 */
@Component
public class TempFileCleanJob extends AbstractJob {

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.FILE_CLEANTEMP_ISWORK){
				return;
			}
			
			String path = FilePathUtil.getAbsolutePath("files")+FilePathUtil.FILE_SEP+"temp";
			long modify = System.currentTimeMillis()-24*60*60*1000;
			
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("清理临时文件：")
				 .append("\n时间：").append(DateUtil.formatDateTime(new Date(modify))).append("之前")
				 .append("\n路径：").append(path);
			logger.info(sbLog.toString());
			
			FileUtil.deleteQuietly(new File(path), modify);
			logger.info("完成清理临时文件");
		} catch (Exception e) {
			logger.error("清理临时文件任务失败", e);
		}
	}

}
