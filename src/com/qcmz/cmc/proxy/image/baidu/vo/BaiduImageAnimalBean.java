package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageAnimalBean {
	/**
	 * 动物名称
	 */
	private String name;
	/**
	 * 置信度，示例：0.5321
	 */
	private String score;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}
