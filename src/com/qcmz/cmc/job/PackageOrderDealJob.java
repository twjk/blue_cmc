package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.PackageOrderProcess;
import com.qcmz.cmc.process.TransComboProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 资讯订阅处理任务
 */
@Component
public class PackageOrderDealJob extends AbstractJob {
	@Autowired
	private PackageOrderProcess packageOrderProcess;
	@Autowired
	private TransComboProcess transComboProcess;
	
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.PACKAGEORDER_DEAL_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}

			transComboProcess.dealComboOrder();
			packageOrderProcess.dealPackage();
		} catch (Exception e) {
			logger.error("优惠套餐订单处理任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.PACKAGEORDER_DEAL_VALIDTIME);
			}
		}
	}

}
