package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class TmGetResp extends Response {
	private TmBean result = new TmBean();

	public TmBean getResult() {
		return result;
	}

	public void setResult(TmBean result) {
		this.result = result;
	}
}
