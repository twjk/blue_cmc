package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RedPacketGetReq extends Request {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 领取用户编号
	 */
	private Long receiverId;

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getPacketId() {
		return packetId;
	}

	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	
}
