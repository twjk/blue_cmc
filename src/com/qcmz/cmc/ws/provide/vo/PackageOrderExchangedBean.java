package com.qcmz.cmc.ws.provide.vo;

public class PackageOrderExchangedBean {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 兑换码
	 */
	private String exchangeCode;
	/**
	 * 兑换用户编号
	 */
	private Long uid;
	/**
	 * 兑换时间
	 */
	private Long exchangeTime;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(Long exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
}
