package com.qcmz.cmc.thrd;

import com.qcmz.cmc.process.UserCrowdTaskProcess;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;

/**
 * 用户众包任务逆地理编码线程
 */
public class UserCrowdTaskRegeocodeThrd extends AbstractThrd {
	/**
	 * 用户任务编号
	 */
	private String utId;
	
	public UserCrowdTaskRegeocodeThrd(String utId) {
		super();
		this.utId = utId;
	}

	@Override
	protected void work() {
		((UserCrowdTaskProcess)SpringUtil.getBean("userCrowdTaskProcess")).regeocodeTask(utId);
	}

	public static void start(String utId){
		UserCrowdTaskRegeocodeThrd geoThrd = new UserCrowdTaskRegeocodeThrd(utId);
		new Thread(geoThrd).start();
	}
}
