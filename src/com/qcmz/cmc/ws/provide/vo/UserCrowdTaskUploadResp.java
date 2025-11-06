package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskUploadResp extends Response {
	private UserCrowdTaskUploadResult result;

	public UserCrowdTaskUploadResult getResult() {
		return result;
	}

	public void setResult(UserCrowdTaskUploadResult result) {
		this.result = result;
	}
}
