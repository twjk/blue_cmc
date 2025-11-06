package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;

import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class RewardBillQueryResp extends Response {
	private List<RewardBillBean> result = new ArrayList<RewardBillBean>();

	public List<RewardBillBean> getResult() {
		return result;
	}

	public void setResult(List<RewardBillBean> result) {
		this.result = result;
	}
}
