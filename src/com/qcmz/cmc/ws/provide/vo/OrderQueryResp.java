package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;

import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class OrderQueryResp extends Response {
	private List<OrderListBean> result = new ArrayList<OrderListBean>();

	public List<OrderListBean> getResult() {
		return result;
	}

	public void setResult(List<OrderListBean> result) {
		this.result = result;
	}
}
