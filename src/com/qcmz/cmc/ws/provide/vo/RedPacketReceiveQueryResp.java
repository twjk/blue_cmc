package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class RedPacketReceiveQueryResp extends Response {
	/**
	 * 红包领取列表
	 */
	private List<RedPacketReceivedBean> result = new ArrayList<RedPacketReceivedBean>();

	public List<RedPacketReceivedBean> getResult() {
		return result;
	}

	public void setResult(List<RedPacketReceivedBean> result) {
		this.result = result;
	}
	
}
