package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskQueryResp extends Response {
	private List<UserCrowdTaskListBean> result = new ArrayList<UserCrowdTaskListBean>();

	public List<UserCrowdTaskListBean> getResult() {
		return result;
	}

	public void setResult(List<UserCrowdTaskListBean> result) {
		this.result = result;
	}
}
