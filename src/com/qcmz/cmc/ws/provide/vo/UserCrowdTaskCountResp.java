package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskCountResp extends Response {
	private UserCrowdTaskCountBean result;

	public UserCrowdTaskCountBean getResult() {
		return result;
	}

	public void setResult(UserCrowdTaskCountBean result) {
		this.result = result;
	}
}
