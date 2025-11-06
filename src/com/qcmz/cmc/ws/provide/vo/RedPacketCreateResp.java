package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class RedPacketCreateResp extends Response {
	private RedPacketCreateResult result = new RedPacketCreateResult();

	public RedPacketCreateResult getResult() {
		return result;
	}

	public void setResult(RedPacketCreateResult result) {
		this.result = result;
	}
}
