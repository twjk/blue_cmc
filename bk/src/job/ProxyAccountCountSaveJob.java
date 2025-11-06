package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.process.ProxyAccountProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class ProxyAccountCountSaveJob extends AbstractJob {
	@Autowired
	private ProxyAccountProcess proxyAccountProcess;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			if(!JobConfig.PROXYACCOUNT_SAVECOUNT_ISWORK){
				return;
			}
			proxyAccountProcess.saveCountAndCheckQuota();
		} catch (Exception e) {
			logger.error("保存代理帐户访问次数任务出错", e);
		}
	}

}
