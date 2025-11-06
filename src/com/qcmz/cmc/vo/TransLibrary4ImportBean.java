package com.qcmz.cmc.vo;


/**
 * 类说明：
 * 修改历史：
 */
public class TransLibrary4ImportBean {
	/**
	 * 编号
	 */
	private Long libId;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 源语言名称
	 */
	private String fromName;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 目标语言名称
	 */
	private String toName;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 分类编号
	 */
	private String cat;
	/**
	 * 分类名称
	 */
	private String catName;
	public TransLibrary4ImportBean() {
	}
	public TransLibrary4ImportBean(Long libId, String from, String src,
			String to, String dst, String cat) {
		super();
		this.libId = libId;
		this.from = from;
		this.src = src;
		this.to = to;
		this.dst = dst;
		this.cat = cat;
	}
	public Long getLibId() {
		return libId;
	}
	public void setLibId(Long libId) {
		this.libId = libId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
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
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
}