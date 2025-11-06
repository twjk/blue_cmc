package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RedPacketCancelReq extends Request {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 用户编号
	 */
	private Long uid;
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
