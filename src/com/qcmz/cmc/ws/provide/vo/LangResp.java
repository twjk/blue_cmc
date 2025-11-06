package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class LangResp extends Response {
	private List<LangBean> result = new ArrayList<LangBean>();

	public List<LangBean> getResult() {
		return result;
	}

	public void setResult(List<LangBean> result) {
		this.result = result;
	}
}
