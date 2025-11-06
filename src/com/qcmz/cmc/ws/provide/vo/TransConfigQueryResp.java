package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransConfigQueryResp extends Response {
	private TransConfigsBean result;

	public TransConfigsBean getResult() {
		return result;
	}

	public void setResult(TransConfigsBean result) {
		this.result = result;
	}
}
