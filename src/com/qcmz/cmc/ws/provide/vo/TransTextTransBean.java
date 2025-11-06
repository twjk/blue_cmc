package com.qcmz.cmc.ws.provide.vo;

public class TransTextTransBean {
	/**
	 * 订单编号
	 */
	private String orderId;	
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	
	public TransTextTransBean() {
		super();
	}
	public TransTextTransBean(String orderId, String from, String to, String src, String dst) {
		super();
		this.orderId = orderId;
		this.from = from;
		this.to = to;
		this.src = src;
		this.dst = dst;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
}
