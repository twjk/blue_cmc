package com.qcmz.cmc.ws.provide.vo;

public class RedPacketPrepayBean {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 支付金额，元
	 */
	private Double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
