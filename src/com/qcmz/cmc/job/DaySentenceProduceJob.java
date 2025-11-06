package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.DaySentenceProcess;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class DaySentenceProduceJob extends AbstractJob {
	@Autowired
	private DaySentenceProcess daySentenceProcess;
	@Autowired
	private LockProcess lockProcess;
	
	/** 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.DAYSENTENCE_PRODUCE_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			daySentenceProcess.produceDaySentence();
		} catch (Exception e) {
			logger.error("每日一句生产任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.DAYSENTENCE_PRODUCE_VALIDTIME);
			}
		}
	}

}
