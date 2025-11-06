package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.cmc.process.TransDiffProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 类说明：翻译差异清除
 * 修改历史：
 */
@Component
public class TransDiffClearJob extends AbstractJob {
	@Autowired
	private TransDiffProcess transDiffProcess;
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
			if(!JobConfig.TRANSDIFF_CLEAR_ISWORK){
				return;
			}
			
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			transDiffProcess.clearDiff();
		} catch (Exception e) {
			logger.error("翻译差异清除任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId);
			}
		}
	}
}
