package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class LocalTaskUserSubGetResp extends Response {
	private LocalTaskUserSubBean result;

	public LocalTaskUserSubBean getResult() {
		return result;
	}

	public void setResult(LocalTaskUserSubBean result) {
		this.result = result;
	}
}
