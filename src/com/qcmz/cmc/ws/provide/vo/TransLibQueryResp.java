package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class TransLibQueryResp extends Response {
	private List<TransLibBean> result = new ArrayList<TransLibBean>();

	public List<TransLibBean> getResult() {
		return result;
	}

	public void setResult(List<TransLibBean> result) {
		this.result = result;
	}
}
