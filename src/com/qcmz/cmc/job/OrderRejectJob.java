package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：超时拒单定时任务
 * 修改历史：
 */
@Component
public class OrderRejectJob extends AbstractJob {
	@Autowired
	private OrderProcess orderProcess;
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
			if(!JobConfig.ORDER_REJECT_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			orderProcess.rejectOrderAndRefund();
		} catch (Exception e) {
			logger.error("超时拒单任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.ORDER_REJECT_VALIDTIME);
			}
		}
	}

}
