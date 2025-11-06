package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.OrderCommissionProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：订单佣金结算任务
 * 修改历史：
 */
@Component
public class OrderCommissionJob extends AbstractJob {
	@Autowired
	private OrderCommissionProcess orderCommissionProcess;
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
			if(!JobConfig.ORDER_COMMISSION_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			orderCommissionProcess.commission();
			
		} catch (Exception e) {
			logger.error("订单佣金结算任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId);
			}
		}
	}

}
