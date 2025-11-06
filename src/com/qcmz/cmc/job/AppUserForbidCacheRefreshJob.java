package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.AppUserForbidMap;
import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：用户黑名单缓存重载
 * 修改历史：
 */
@Component
public class AppUserForbidCacheRefreshJob extends AbstractJob {
	@Autowired
	private AppUserForbidMap appUserForbidMap;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.APPUSERFORBIDCACHE_REFRESH_ISWORK){
				return;
			}
			appUserForbidMap.refresh();
		} catch (Exception e) {
			logger.error("用户黑名单缓存重载任务出错", e);
		}
	}

}
