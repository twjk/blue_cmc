package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.process.TransDiffProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：翻译差异入库
 * 修改历史：
 */
@Component
public class TransDiffSaveJob extends AbstractJob {
	@Autowired
	private TransDiffProcess transDiffProcess;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.TRANSDIFF_SAVE_ISWORK){
				return;
			}
			transDiffProcess.saveDiff();;
		} catch (Exception e) {
			logger.error("翻译差异入库任务出错", e);
		}
	}

}
