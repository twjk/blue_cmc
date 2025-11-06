package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.process.TransWordProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：翻译日志分词任务
 * 修改历史：
 */
@Component
public class WordAnalysisFromLogJob extends AbstractJob {
	@Autowired
	private TransWordProcess transWordProcess;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.WORD_ANALYSISFROMLOG_ISWORK
					|| TransConfig.TRANS_LOG_ON==0){
				return;
			}

			transWordProcess.analysisWordFromLog();
		} catch (Exception e) {
			logger.error("翻译日志分词任务出错", e);
		}
	}

}
