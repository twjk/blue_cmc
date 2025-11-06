package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdSubjectQueryResp extends Response {
	private List<UserCrowdSubjectBean> result = new ArrayList<UserCrowdSubjectBean>();

	public List<UserCrowdSubjectBean> getResult() {
		return result;
	}

	public void setResult(List<UserCrowdSubjectBean> result) {
		this.result = result;
	}
}
