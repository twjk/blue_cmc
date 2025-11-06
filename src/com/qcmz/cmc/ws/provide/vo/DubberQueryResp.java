package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class DubberQueryResp extends Response {
	private List<DubberBean> result = new ArrayList<DubberBean>();

	public List<DubberBean> getResult() {
		return result;
	}

	public void setResult(List<DubberBean> result) {
		this.result = result;
	}
	
}
