package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class CatQueryResp extends Response {
	private List<CatListBean> result = new ArrayList<CatListBean>();

	public List<CatListBean> getResult() {
		return result;
	}

	public void setResult(List<CatListBean> result) {
		this.result = result;
	}
}
