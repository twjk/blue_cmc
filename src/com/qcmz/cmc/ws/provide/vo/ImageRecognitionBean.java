package com.qcmz.cmc.ws.provide.vo;

public class ImageRecognitionBean {
	/**
	 * 识别内容
	 */
	private String content;
	/**
	 * 置信值
	 */
	private String score;
	
	public ImageRecognitionBean() {
		super();
	}
	public ImageRecognitionBean(String content, String score) {
		super();
		this.content = content;
		this.score = score;
	}
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
