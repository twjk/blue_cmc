package com.qcmz.cmc.job;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.stereotype.Component;

/**
 * 类说明：定时任务监听
 * 修改历史：
 */
@Component
public class MySchedulerListener extends SchedulerListenerSupport {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		logger.error("jobUnscheduled:"+triggerKey.getName());
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		logger.error("schedulerError:"+msg, cause.getUnderlyingException());
	}
	
}
