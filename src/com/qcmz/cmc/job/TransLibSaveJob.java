package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.process.TransLibProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：译库入库
 * 修改历史：
 */
@Component
public class TransLibSaveJob extends AbstractJob {
	@Autowired
	private TransLibProcess transLibProcess;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.TRANLIB_SAVE_ISWORK){
				return;
			}
			transLibProcess.saveTransLib();
		} catch (Exception e) {
			logger.error("译库入库任务出错", e);
		}
	}

}
