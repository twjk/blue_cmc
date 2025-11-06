package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class EvalQueryResp extends Response {
	private List<EvalBean> result = new ArrayList<EvalBean>();

	public List<EvalBean> getResult() {
		return result;
	}

	public void setResult(List<EvalBean> result) {
		this.result = result;
	}
}
