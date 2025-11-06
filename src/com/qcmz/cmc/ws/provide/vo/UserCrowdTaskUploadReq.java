package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskUploadReq extends Request {
	private UserCrowdTaskUploadBean bean = new UserCrowdTaskUploadBean();

	public UserCrowdTaskUploadBean getBean() {
		return bean;
	}

	public void setBean(UserCrowdTaskUploadBean bean) {
		this.bean = bean;
	}
}
