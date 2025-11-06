package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class TransCorrectQueryResp extends Response {
	private List<TransCorrectBean> result = new ArrayList<TransCorrectBean>();

	public List<TransCorrectBean> getResult() {
		return result;
	}

	public void setResult(List<TransCorrectBean> result) {
		this.result = result;
	}
}
