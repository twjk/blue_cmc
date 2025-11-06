package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：视频口译语言
 * 修改历史：
 */
public class TransVideoLangBean {
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	public TransVideoLangBean() {
		super();
	}
	public TransVideoLangBean(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
