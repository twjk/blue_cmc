package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.CrowdTaskDataProcess;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 用户众包任务删除文件
 */
@Component
public class UserCrowdTaskDeleteFileJob extends AbstractJob {
	@Autowired
	private CrowdTaskDataProcess crowdTaskDataProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.USERCROWDTASK_DELETEFILE_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			crowdTaskDataProcess.deleteUserCrowdTaskFile();
		} catch (Exception e) {
			logger.error("用户众包任务删除文件任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.USERCROWDTASK_DELETEFILE_VALIDTIME);
			}
		}
	}

}
