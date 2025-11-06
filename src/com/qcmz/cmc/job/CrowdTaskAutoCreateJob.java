package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.CrowdTaskProcess;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.framework.job.AbstractJob;

@Component
public class CrowdTaskAutoCreateJob extends AbstractJob {
	@Autowired
	private CrowdTaskProcess crowdTaskProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.CROWDTASK_AUTOCREATE_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			crowdTaskProcess.autoCreate();
		} catch (Exception e) {
			logger.error("众包任务自动创建任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.CROWDTASK_AUTOCREATE_VALIDTIME);
			}
		}
	}

}
