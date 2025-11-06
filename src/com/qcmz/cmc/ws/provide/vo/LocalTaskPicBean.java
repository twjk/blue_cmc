package com.qcmz.cmc.ws.provide.vo;

public class LocalTaskPicBean {
	/**
	 * 图片地址
	 */
	private String picUrl;
	
	public LocalTaskPicBean() {
		super();
	}
	
	public LocalTaskPicBean(String picUrl) {
		super();
		this.picUrl = picUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
