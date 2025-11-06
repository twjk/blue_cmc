package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class LocalCompanyGetResp extends Response {
	private LocalCompanyBean result;

	public LocalCompanyBean getResult() {
		return result;
	}

	public void setResult(LocalCompanyBean result) {
		this.result = result;
	}
}
