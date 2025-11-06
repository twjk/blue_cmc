package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskVerifyQueryReq extends Request {
	private UserCrowdTaskVerifyQueryBean bean = new UserCrowdTaskVerifyQueryBean();

	public UserCrowdTaskVerifyQueryBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdTaskVerifyQueryBean bean) {
		this.bean = bean;
	}
}
