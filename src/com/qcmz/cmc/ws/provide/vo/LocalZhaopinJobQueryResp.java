package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class LocalZhaopinJobQueryResp extends Response {
	private List<LocalZhaopinJobListBean> result = new ArrayList<LocalZhaopinJobListBean>();

	public List<LocalZhaopinJobListBean> getResult() {
		return result;
	}

	public void setResult(List<LocalZhaopinJobListBean> result) {
		this.result = result;
	}
}
