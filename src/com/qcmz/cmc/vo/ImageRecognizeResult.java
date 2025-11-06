package com.qcmz.cmc.vo;

public class ImageRecognizeResult {
	/**
	 * 名称
	 */
	private String content;
	/**
	 * 置信度
	 */
	private String score;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}
