package com.qcmz.cmc.ws.provide.vo;

public class TransLibAddBean {
	/**
	 * 译库类型
	 */
	private String libType;
	/**
	 * 原文语言
	 */
	private String from;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文语言
	 */
	private String to;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 双向互译
	 */
	private Integer twoway;
	/**
	 * 分类
	 */
	private String cat;
	/**
	 * 操作人
	 */
	private String operCd;
	/**
	 * 操作人姓名
	 */
	private String operName;
	public String getLibType() {
		return libType;
	}
	public void setLibType(String libType) {
		this.libType = libType;
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
	public Integer getTwoway() {
		return twoway;
	}
	public void setTwoway(Integer twoway) {
		this.twoway = twoway;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getOperCd() {
		return operCd;
	}
	public void setOperCd(String operCd) {
		this.operCd = operCd;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
}
