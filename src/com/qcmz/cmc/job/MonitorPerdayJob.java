package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.MonitorProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：每日监控任务
 * 修改历史：
 */
@Component
public class MonitorPerdayJob extends AbstractJob {
	@Autowired
	private MonitorProcess monitorProcess;
	@Autowired
	private LockProcess lockProcess;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.MONITOR_PERDAY_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			monitorProcess.monitorPerday();
		} catch (Exception e) {
			logger.error("每日监控任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId);
			}
		}
	}

}
