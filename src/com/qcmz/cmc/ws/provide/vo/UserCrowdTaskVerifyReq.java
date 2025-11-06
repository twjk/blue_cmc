package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskVerifyReq extends Request {
	private UserCrowdTaskVerifyBean bean = new UserCrowdTaskVerifyBean();

	public UserCrowdTaskVerifyBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdTaskVerifyBean bean) {
		this.bean = bean;
	}
}
