package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class TransRespV2 extends Response{
	/**
	 * 翻译结果
	 */
	private TransResultV2 result;

	public TransResultV2 getResult() {
		return result;
	}

	public void setResult(TransResultV2 result) {
		this.result = result;
	}
}
