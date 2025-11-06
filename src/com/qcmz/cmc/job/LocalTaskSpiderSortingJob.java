package com.qcmz.cmc.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.LocalTaskSpiderProcess;
import com.qcmz.cmc.process.LockProcess;
import com.qcmz.framework.job.AbstractJob;
import com.qcmz.framework.util.DateUtil;

@Component
public class LocalTaskSpiderSortingJob extends AbstractJob {
	@Autowired
	private LocalTaskSpiderProcess localTaskSpiderProcess;
	@Autowired
	private LockProcess lockProcess;
	
	@Override
	protected void work() {
		Long lockId = null;
		try {
			if(!JobConfig.LOALTASK_SPIDERSORTING_ISWORK){
				return;
			}
			lockId = lockProcess.addLock4Job(DictConstant.DICT_LOCKTYPE_JOB, getClassName());
			if(lockId==null){
				return;
			}
			
			Map<String, String> params = new HashMap<String, String>();
			Date d = DateUtil.addDay(DateUtil.getSysDateOnly(), -2);
			params.put("spdDateS", DateUtil.formatDate(d));			
			localTaskSpiderProcess.sorting(params);
		} catch (Exception e) {
			logger.error("同城采集结果分拣任务出错", e);
		} finally{
			if(lockId!=null){
				lockProcess.updateExpireTime(lockId, JobConfig.LOALTASK_SPIDERSORTING_VALIDTIME);
			}
		}
	}
}
