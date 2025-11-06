package com.qcmz.cmc.ws.provide.vo;

public class JobArticlePicBean {
	/**
	 * 图片链接
	 */
	private String picUrl;
	/**
	 * 缩略图链接
	 */
	private String thumbUrl;
	
	public JobArticlePicBean() {
		super();
	}
	
	public JobArticlePicBean(String picUrl, String thumbUrl) {
		super();
		this.picUrl = picUrl;
		if(thumbUrl==null || thumbUrl.equals("")){
			thumbUrl = picUrl;
		}
		this.thumbUrl = thumbUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
