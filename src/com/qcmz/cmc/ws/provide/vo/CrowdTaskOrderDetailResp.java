package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class CrowdTaskOrderDetailResp extends Response {
	private CrowdTaskOrderDetailBean result;

	public CrowdTaskOrderDetailBean getResult() {
		return result;
	}

	public void setResult(CrowdTaskOrderDetailBean result) {
		this.result = result;
	}
}
