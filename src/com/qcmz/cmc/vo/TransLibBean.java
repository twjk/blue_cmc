package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLibBean {
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
	/**
	 * 是否
	 */
	private int twoway;
	
	public TransLibBean() {
		super();
	}
	
	public TransLibBean(String from, String src, String to, String dst, Integer twoway) {
		super();
		this.from = from;
		this.src = src;
		this.to = to;
		this.dst = dst;
		this.twoway = twoway;
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
	public int getTwoway() {
		return twoway;
	}
	public void setTwoway(int twoway) {
		this.twoway = twoway;
	}
}
