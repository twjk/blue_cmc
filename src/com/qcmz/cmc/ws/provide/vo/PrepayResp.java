package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class PrepayResp extends Response {
	private PrepayResult result;

	public PrepayResult getResult() {
		return result;
	}

	public void setResult(PrepayResult result) {
		this.result = result;
	}
}