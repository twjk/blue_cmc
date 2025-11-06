package com.qcmz.cmc.ws.provide.vo;

public class ImageRecognitionCatBean {
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 分类名称
	 */
	private String catName;
	/**
	 * 分类图标
	 */
	private String catIcon;
	/**
	 * 是否收费，0免费1收费
	 */
	private Integer charge;
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatIcon() {
		return catIcon;
	}
	public void setCatIcon(String catIcon) {
		this.catIcon = catIcon;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
}
