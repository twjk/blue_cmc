package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.process.ApiKeyCallMonitorProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 5, 2014 3:24:46 PM
 * 修改历史：
 */
@Component
public class ApiKeyCallMonitorJob extends AbstractJob {
	@Autowired
	private ApiKeyCallMonitorProcess apiKeyCallMonitorProcess;
	
	/** 
	 * 
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 5, 2014 3:24:46 PM
	 * 修改历史：
	 */
	@Override
	public void work() {
		if(!JobConfig.APIKEY_CALLMONITOR_ISWORK){
			logger.info("ApiKey日访问量监控任务关闭");
			return;
		}
		try {
			logger.info("开始ApiKey日访问量监控任务");
			apiKeyCallMonitorProcess.monitorApiKeyTodayCall();
		} catch (Exception e) {
			logger.error("ApiKey日访问量监控出错", e);
		}
	}
}
