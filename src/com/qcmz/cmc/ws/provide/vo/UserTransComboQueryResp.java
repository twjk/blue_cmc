package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class UserTransComboQueryResp extends Response {
	private List<UserTransComboBean> result = new ArrayList<UserTransComboBean>();

	public List<UserTransComboBean> getResult() {
		return result;
	}

	public void setResult(List<UserTransComboBean> result) {
		this.result = result;
	}
}
