package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicIPictureTransBean {
	private String id;
	private String descr;
	private int left;
	private int top;
	
	public TransPicIPictureTransBean() {
		super();
	}
	
	public TransPicIPictureTransBean(String id, String descr, int left, int top) {
		super();
		this.id = id;
		this.descr = descr;
		this.left = left;
		this.top = top;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
}
