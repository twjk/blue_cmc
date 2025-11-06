package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.RedPacketProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 红包过期处理任务
 */
@Component
public class RedPacketExpireDealJob extends AbstractJob {
	@Autowired
	private RedPacketProcess redPacketProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.REDPACKET_EXPIREDEAL_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}

			redPacketProcess.expireDeal();
		} catch (Exception e) {
			logger.error("红包过期处理任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId);
			}
		}
	}

}
