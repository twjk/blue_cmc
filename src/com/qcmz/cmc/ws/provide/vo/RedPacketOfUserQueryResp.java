package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class RedPacketOfUserQueryResp extends Response {
	private RedPacketUserBean result;

	public RedPacketUserBean getResult() {
		return result;
	}

	public void setResult(RedPacketUserBean result) {
		this.result = result;
	}
	
}
