package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPriceQueryBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 翻译途径
	 */
	private String transWay;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransWay() {
		return transWay;
	}

	public void setTransWay(String transWay) {
		this.transWay = transWay;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
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
}
