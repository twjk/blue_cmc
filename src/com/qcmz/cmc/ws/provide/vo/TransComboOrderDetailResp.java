package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class TransComboOrderDetailResp extends Response {
	private TransComboOrderDetailBean result = null;

	public TransComboOrderDetailBean getResult() {
		return result;
	}

	public void setResult(TransComboOrderDetailBean result) {
		this.result = result;
	}
}
