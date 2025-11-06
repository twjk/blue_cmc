package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class UserCrowdNewSubjectQueryResp extends Response {
	private List<CrowdSubjectBean> result = new ArrayList<CrowdSubjectBean>();

	public List<CrowdSubjectBean> getResult() {
		return result;
	}

	public void setResult(List<CrowdSubjectBean> result) {
		this.result = result;
	}
}
