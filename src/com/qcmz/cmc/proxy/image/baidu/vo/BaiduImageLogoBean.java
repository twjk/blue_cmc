package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageLogoBean {
	/**
	 * 品牌名称
	 */
	private String name;
	/**
	 * 置信度，示例：0.5321
	 */
	private String probability;
	/**
	 * 位置信息
	 */
	private BaiduImageLogoLocationBean location;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	public BaiduImageLogoLocationBean getLocation() {
		return location;
	}
	public void setLocation(BaiduImageLogoLocationBean location) {
		this.location = location;
	}
}
