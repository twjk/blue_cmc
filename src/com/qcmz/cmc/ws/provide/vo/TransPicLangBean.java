package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：可以图片翻译的语言列表
 * 修改历史：
 */
public class TransPicLangBean {
	/**
	 * 翻译途径
	 */
	private String transWay;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	public String getTransWay() {
		return transWay;
	}
	public void setTransWay(String transWay) {
		this.transWay = transWay;
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
