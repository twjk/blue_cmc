package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class CrowdTaskCompletionQueryResp extends Response {
	private List<CrowdTaskCompletionListBean> result = new ArrayList<CrowdTaskCompletionListBean>();

	public List<CrowdTaskCompletionListBean> getResult() {
		return result;
	}

	public void setResult(List<CrowdTaskCompletionListBean> result) {
		this.result = result;
	}
}
