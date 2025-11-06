package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class RedPacketGetResp extends Response {
	private RedPacketBean result = null;

	public RedPacketBean getResult() {
		return result;
	}

	public void setResult(RedPacketBean result) {
		this.result = result;
	}
}
