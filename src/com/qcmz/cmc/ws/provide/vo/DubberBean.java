package com.qcmz.cmc.ws.provide.vo;

public class DubberBean {
	/**
	 * 配音员编号
	 */
	private Long dubberId;
	/**
	 * 名称
	 */
	private String title;
	/**
	 * 头像
	 */
	private String icon;
	/**
	 * 特长
	 */
	private String specialty;
	/**
	 * 风格
	 */
	private String style;
	/**
	 * 试听语音
	 */
	private String audition;
	/**
	 * 对应商品
	 */
	private ProductItemBean item;
	public Long getDubberId() {
		return dubberId;
	}
	public void setDubberId(Long dubberId) {
		this.dubberId = dubberId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getAudition() {
		return audition;
	}
	public void setAudition(String audition) {
		this.audition = audition;
	}
	public ProductItemBean getItem() {
		return item;
	}
	public void setItem(ProductItemBean item) {
		this.item = item;
	}
}
