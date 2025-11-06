package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class RedPacketReceiveResp extends Response {
	private RedPacketReceiveResult result = null;

	public RedPacketReceiveResult getResult() {
		return result;
	}

	public void setResult(RedPacketReceiveResult result) {
		this.result = result;
	}
}
