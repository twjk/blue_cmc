package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class OrderDetailResp extends Response {
	private OrderDetailBean result;

	public OrderDetailBean getResult() {
		return result;
	}

	public void setResult(OrderDetailBean result) {
		this.result = result;
	}
}
