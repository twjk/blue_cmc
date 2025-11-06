package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.process.TransLibProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：译库缓存命中次数入库任务
 * 修改历史：
 */
@Component
public class TransLibCacheHitSaveJob extends AbstractJob {
	@Autowired
	private TransLibProcess transLibProcess;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.TRANLIBCACHE_SAVEHIT_ISWORK){
				return;
			}
			transLibProcess.saveHitCount();
		} catch (Exception e) {
			logger.error("译库缓存命中次数入库任务出错", e);
		}
	}

}
