package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：翻译收藏信息
 * 修改历史：
 */
public class TransFavBean {
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 译文
	 */
	private String dst;
	
	public TransFavBean() {
		super();
	}
	
	public TransFavBean(String from, String src, String to, String dst) {
		super();
		this.from = from;
		this.src = src;
		this.to = to;
		this.dst = dst;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
}
