package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskQcQueryResp extends Response {
	private List<UserCrowdTaskQcListBean> result = new ArrayList<UserCrowdTaskQcListBean>();

	public List<UserCrowdTaskQcListBean> getResult() {
		return result;
	}

	public void setResult(List<UserCrowdTaskQcListBean> result) {
		this.result = result;
	}
}
