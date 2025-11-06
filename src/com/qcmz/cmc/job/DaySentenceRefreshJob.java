package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.DaySentenceMap;
import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class DaySentenceRefreshJob extends AbstractJob {
	@Autowired
	private DaySentenceMap daySentenceMap;
	/** 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.DAYSENTENCE_REFRESH_ISWORK){
				return;
			}
			daySentenceMap.refresh();
		} catch (Exception e) {
			logger.error("每日一句缓存刷新任务出错", e);
		}
	}

}
