package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class TransComboQueryResp extends Response {
	private List<TransComboBean> result = new ArrayList<TransComboBean>();

	public List<TransComboBean> getResult() {
		return result;
	}

	public void setResult(List<TransComboBean> result) {
		this.result = result;
	}
}
