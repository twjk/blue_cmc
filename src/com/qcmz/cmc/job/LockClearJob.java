package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：清除过期锁
 * 修改历史：
 */
@Component
public class LockClearJob extends AbstractJob {
	@Autowired
	private ILockService lockService;
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
			if(!JobConfig.LOCK_CLEAR_ISWORK){
				return;
			}
			
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			lockService.clearExpiredLock();
		} catch (Exception e) {
			logger.error("清除过期锁失败", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.LOCK_CLEAR_VALIDTIME);
			}
		}
	}
}
