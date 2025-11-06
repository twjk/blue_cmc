package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdSubjectAnswerReq extends Request {
	private UserCrowdSubjectAnswerBean bean = new UserCrowdSubjectAnswerBean();

	public UserCrowdSubjectAnswerBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdSubjectAnswerBean bean) {
		this.bean = bean;
	}
}
