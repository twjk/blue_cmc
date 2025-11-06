package com.qcmz.cmc.thrd;

import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.cmc.process.LocalTaskSpiderProcess;
import com.qcmz.framework.thrd.AbstractThrd;

/**
 * 类说明：同城采集结果分拣
 * 修改历史：
 */
public class LocalSpiderSortingThrd extends AbstractThrd {
	private LocalTaskSpiderProcess localTaskSpiderProcess;
	/**
	 * 语言
	 */
	private CmcLtSpd spd;

	public LocalSpiderSortingThrd(LocalTaskSpiderProcess localTaskSpiderProcess, CmcLtSpd spd) {
		super();
		this.localTaskSpiderProcess = localTaskSpiderProcess;
		this.spd = spd;
	}

	@Override
	protected void work() {
		bean = localTaskSpiderProcess.sorting(spd);
	}
}
