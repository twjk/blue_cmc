package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class DaySentenceLangResp extends Response {
	private List<DaySentenceLangBean> result = new ArrayList<DaySentenceLangBean>();

	public List<DaySentenceLangBean> getResult() {
		return result;
	}

	public void setResult(List<DaySentenceLangBean> result) {
		this.result = result;
	}
}
