package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.UserCrowdTaskProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 用户众包任务过期取消
 */
@Component
public class UserCrowdTaskCancelJob extends AbstractJob {
	@Autowired
	private UserCrowdTaskProcess userCrowdTaskProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.USERCROWDTASK_CANCEL_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			userCrowdTaskProcess.cancelOvertime();
		} catch (Exception e) {
			logger.error("用户众包任务过期取消任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.USERCROWDTASK_CANCEL_VALIDTIME);
			}
		}
	}

}
