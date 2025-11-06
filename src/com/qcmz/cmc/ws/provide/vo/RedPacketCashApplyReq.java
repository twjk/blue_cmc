package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RedPacketCashApplyReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 提现金额
	 */
	private Double amount;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
