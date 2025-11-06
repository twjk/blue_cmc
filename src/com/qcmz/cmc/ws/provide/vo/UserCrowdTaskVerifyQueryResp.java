package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdTaskVerifyQueryResp extends Response {
	private List<UserCrowdTaskVerifyListBean> result = new ArrayList<UserCrowdTaskVerifyListBean>();

	public List<UserCrowdTaskVerifyListBean> getResult() {
		return result;
	}

	public void setResult(List<UserCrowdTaskVerifyListBean> result) {
		this.result = result;
	}
}
