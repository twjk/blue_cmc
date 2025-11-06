package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;

import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class CrowdTaskOrderQueryResp extends Response {
	private List<CrowdTaskOrderListBean> result = new ArrayList<CrowdTaskOrderListBean>();

	public List<CrowdTaskOrderListBean> getResult() {
		return result;
	}

	public void setResult(List<CrowdTaskOrderListBean> result) {
		this.result = result;
	}
}
