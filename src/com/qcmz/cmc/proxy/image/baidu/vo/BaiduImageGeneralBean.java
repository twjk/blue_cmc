package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageGeneralBean {
	/**
	 * 物体或场景名称
	 */
	private String keyword;
	/**
	 * 置信度，示例：0.5321
	 */
	private String score;
	/**
	 * 识别结果的上层标签，即所属大类及细分类
	 */
	private String root;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
}
