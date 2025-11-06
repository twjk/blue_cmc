package com.qcmz.cmc.job;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：资讯标签缓存重载
 * 修改历史：
 */
@Component
public class ArticleTagCacheRefreshJob extends AbstractJob {
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.ARTICLETAGCACHE_REFRESH_ISWORK){
				return;
			}
		} catch (Exception e) {
			logger.error("资讯标签缓存重载任务出错", e);
		}
	}

}
