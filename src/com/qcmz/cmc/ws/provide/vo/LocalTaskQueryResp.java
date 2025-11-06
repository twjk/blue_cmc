package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class LocalTaskQueryResp extends Response {
	private List<LocalTaskListBean> result = new ArrayList<LocalTaskListBean>();

	public List<LocalTaskListBean> getResult() {
		return result;
	}

	public void setResult(List<LocalTaskListBean> result) {
		this.result = result;
	}
}
