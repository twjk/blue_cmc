package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskQcQueryReq extends Request {
	private UserCrowdTaskQcQueryBean bean = new UserCrowdTaskQcQueryBean();

	public UserCrowdTaskQcQueryBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdTaskQcQueryBean bean) {
		this.bean = bean;
	}
}
