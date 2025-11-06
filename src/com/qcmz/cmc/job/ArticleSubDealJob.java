package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.ArticleOrderProcess;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.framework.job.AbstractJob;

/**
 * 资讯订阅处理任务
 */
@Component
public class ArticleSubDealJob extends AbstractJob {
	@Autowired
	private ArticleOrderProcess articleOrderProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.ARTICLESUB_SUCCESSDEAL_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}

			articleOrderProcess.dealArticleSub();
		} catch (Exception e) {
			logger.error("资讯订阅处理任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.ARTICLESUB_SUCCESSDEAL_VALIDTIME);
			}
		}
	}

}
