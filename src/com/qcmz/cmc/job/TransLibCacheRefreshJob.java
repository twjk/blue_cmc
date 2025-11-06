package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransLibMap;
import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：译库缓存重新加载
 * 修改历史：
 */
@Component
public class TransLibCacheRefreshJob extends AbstractJob {
	@Autowired
	private TransLibMap transLibMap;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.TRANLIBCACHE_REFRESH_ISWORK){
				return;
			}
			transLibMap.refresh();
		} catch (Exception e) {
			logger.error("译库缓存重新加载任务出错", e);
		}
	}

}
