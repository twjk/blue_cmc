package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskGetResp extends Response {
	private UserCrowdTaskDetailBean result;

	public UserCrowdTaskDetailBean getResult() {
		return result;
	}

	public void setResult(UserCrowdTaskDetailBean result) {
		this.result = result;
	}
}
