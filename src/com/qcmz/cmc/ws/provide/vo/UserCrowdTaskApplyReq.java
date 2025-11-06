package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskApplyReq extends Request {
	private UserCrowdTaskApplyBean bean = new UserCrowdTaskApplyBean();

	public UserCrowdTaskApplyBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdTaskApplyBean bean) {
		this.bean = bean;
	}
}
