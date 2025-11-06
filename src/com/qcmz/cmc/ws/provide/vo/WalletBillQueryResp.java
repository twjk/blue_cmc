package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;

import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class WalletBillQueryResp extends Response {
	private List<WalletBillBean> result = new ArrayList<WalletBillBean>();

	public List<WalletBillBean> getResult() {
		return result;
	}

	public void setResult(List<WalletBillBean> result) {
		this.result = result;
	}
}
