package com.qcmz.cmc.ws.provide.vo;

public class RedPacketCreateResult {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 预支付结果
	 */
	private PrepayResult prepayResult;
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public PrepayResult getPrepayResult() {
		return prepayResult;
	}
	public void setPrepayResult(PrepayResult prepayResult) {
		this.prepayResult = prepayResult;
	}
}
