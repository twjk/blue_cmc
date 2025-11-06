package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeSentenceBean {
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
	 * 分类编号
	 */
	private String catId;
	/**
	 * 分类名称
	 */
	private String catName;
	
	public ThemeSentenceBean() {
		super();
	}
	
	public ThemeSentenceBean(String from, String src, String to, String dst,
			String catId) {
		super();
		this.from = from;
		this.src = src;
		this.to = to;
		this.dst = dst;
		this.catId = catId;
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
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
}
