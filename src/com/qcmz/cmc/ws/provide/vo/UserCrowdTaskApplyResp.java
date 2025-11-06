package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskApplyResp extends Response {
	private UserCrowdTaskApplyResult result;

	public UserCrowdTaskApplyResult getResult() {
		return result;
	}

	public void setResult(UserCrowdTaskApplyResult result) {
		this.result = result;
	}
}
