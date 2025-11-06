package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LocalTaskProcess;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：就业精选订阅通知定时任务
 */
@Component
public class LocalTaskSubNotifyJob extends AbstractJob {
	@Autowired
	private LocalTaskProcess localTaskProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.LOALTASK_SUBNOTIFY_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			localTaskProcess.subNotify();
		} catch (Exception e) {
			logger.error("就业精选订阅通知任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.LOALTASK_SUBNOTIFY_VALIDTIME);
			}
		}
	}

}
