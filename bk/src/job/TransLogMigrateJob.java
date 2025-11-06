package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.process.TransLogProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：翻译日志迁移
 * 修改历史：
 */
@Component
public class TransLogMigrateJob extends AbstractJob {
	@Autowired
	private TransLogProcess transLogProcess;
		
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.TRANSLOG_MIGRATE_ISWORK
					|| TransConfig.TRANS_LOG_ON==0){
				return;
			}

			transLogProcess.migrate();
			transLogProcess.uploadOSS();			
		} catch (Exception e) {
			logger.error("翻译日志迁移任务出错", e);
		}
	}

}
