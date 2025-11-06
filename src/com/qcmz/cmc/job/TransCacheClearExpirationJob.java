package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransResultMap;
import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：清除过期翻译缓存任务
 * 修改历史：
 */
@Component
public class TransCacheClearExpirationJob extends AbstractJob {
	@Autowired
	private TransResultMap transResultMap;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.TRANSCACHE_CLEAREXPIRATION_ISWORK){
				return;
			}
			transResultMap.clearExpiration();
		} catch (Exception e) {
			logger.error("清除过期翻译缓存任务出错", e);
		}
	}

}
