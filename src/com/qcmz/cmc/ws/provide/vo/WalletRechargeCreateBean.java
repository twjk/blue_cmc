package com.qcmz.cmc.ws.provide.vo;

public class WalletRechargeCreateBean {
	/**
	 * 用户编号
	 */
	private Long uid;	
	/**
	 * 充值金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
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
