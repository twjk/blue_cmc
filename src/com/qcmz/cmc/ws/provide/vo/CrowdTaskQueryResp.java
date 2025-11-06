package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class CrowdTaskQueryResp extends Response {
	private List<CrowdTaskListBean> result = new ArrayList<CrowdTaskListBean>();

	public List<CrowdTaskListBean> getResult() {
		return result;
	}

	public void setResult(List<CrowdTaskListBean> result) {
		this.result = result;
	}
}
