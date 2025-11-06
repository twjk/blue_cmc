package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class DaySentenceQueryResp extends Response {
	private List<DaySentenceBean> result = new ArrayList<DaySentenceBean>();

	public List<DaySentenceBean> getResult() {
		return result;
	}

	public void setResult(List<DaySentenceBean> result) {
		this.result = result;
	}
	
}
