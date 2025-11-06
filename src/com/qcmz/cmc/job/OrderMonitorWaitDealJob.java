package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.MonitorProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：待处理订单监控任务
 * 修改历史：
 */
@Component
public class OrderMonitorWaitDealJob extends AbstractJob {
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
			if(!JobConfig.ORDERMONITOR_WAITDEAL_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}

			monitorProcess.monitorWaitDeal();
		} catch (Exception e) {
			logger.error("待处理订单监控任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.ORDERMONITOR_WAITDEAL_VALIDTIME);
			}
		}
	}

}
