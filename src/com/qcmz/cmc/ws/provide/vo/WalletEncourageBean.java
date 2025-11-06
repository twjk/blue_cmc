package com.qcmz.cmc.ws.provide.vo;

public class WalletEncourageBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 激励类型
	 */
	private Integer encourageType;
	/**
	 * 激励标识
	 */
	private String orderId;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getEncourageType() {
		return encourageType;
	}
	public void setEncourageType(Integer encourageType) {
		this.encourageType = encourageType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
