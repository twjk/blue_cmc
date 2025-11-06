package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class RedPacketAccountResp extends Response {
	private RedPacketAccountBean result = null;

	public RedPacketAccountBean getResult() {
		return result;
	}

	public void setResult(RedPacketAccountBean result) {
		this.result = result;
	}
}
