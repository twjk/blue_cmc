package com.qcmz.cmc.ws.provide.vo;

public class OrderEvalAddBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 评价编号
	 */
	private Long evalId;
	/**
	 * 评价标签，多个用;分隔
	 */
	private String evalTag;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getEvalId() {
		return evalId;
	}
	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}
	public String getEvalTag() {
		return evalTag;
	}
	public void setEvalTag(String evalTag) {
		this.evalTag = evalTag;
	}
}
