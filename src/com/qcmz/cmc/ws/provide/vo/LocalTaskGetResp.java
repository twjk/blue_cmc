package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class LocalTaskGetResp extends Response {
	private LocalTaskBean result;

	public LocalTaskBean getResult() {
		return result;
	}

	public void setResult(LocalTaskBean result) {
		this.result = result;
	}
}
