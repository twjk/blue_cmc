package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageStarStarBean {
	/**
	 * 敏感人物名称
	 */
	private String name;
	/**
	 * 人脸相似度
	 */
	private String probability;
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
}
