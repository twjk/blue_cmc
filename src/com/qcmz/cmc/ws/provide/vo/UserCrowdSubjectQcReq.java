package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdSubjectQcReq extends Request {
	private UserCrowdSubjectQcBean bean = new UserCrowdSubjectQcBean();

	public UserCrowdSubjectQcBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdSubjectQcBean bean) {
		this.bean = bean;
	}
}
