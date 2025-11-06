package com.qcmz.cmc.ws.provide.vo;

public class CatListBean {
    private Long catId;
	private String catName;
	private String catIcon;
	/**
	 * 父分类编号
	 */
	private Long parentId;
	
	public CatListBean() {
		super();
	}
	public CatListBean(Long catId, Long parentId, String catName, String catIcon) {
		super();
		this.catId = catId;
		this.parentId = parentId;
		this.catName = catName;
		this.catIcon = catIcon;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
}
