package com.qcmz.cmc.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.process.CrowdTaskCountProcess;
import com.qcmz.framework.job.AbstractJob;

@Component
public class CrowdTaskCountSaveJob extends AbstractJob {
	@Autowired
	private CrowdTaskCountProcess crowdTaskCountProcess;
	
	@Override
	protected void work() {
		try {
			if(!JobConfig.CROWDTASK_COUNTSAVE_ISWORK){
				return;
			}
			crowdTaskCountProcess.saveCount();
		} catch (Exception e) {
			logger.error("众包任务计数入库任务出错", e);
		}
	}

}
