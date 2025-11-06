package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class CrowdTaskGetResp extends Response {
	private CrowdTaskDetailBean result;

	public CrowdTaskDetailBean getResult() {
		return result;
	}

	public void setResult(CrowdTaskDetailBean result) {
		this.result = result;
	}
}
