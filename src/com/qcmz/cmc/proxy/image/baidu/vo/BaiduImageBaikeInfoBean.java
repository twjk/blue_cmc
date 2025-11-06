package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageBaikeInfoBean {
	/**
	 * 对应识别结果百度百科页面链接
	 */
	private String baike_url;
	/**
	 * 对应识别结果百科图片链接
	 */
	private String image_url;
	/**
	 * 对应识别结果百科内容描述
	 */
	private String description;
	public String getBaike_url() {
		return baike_url;
	}
	public void setBaike_url(String baike_url) {
		this.baike_url = baike_url;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
