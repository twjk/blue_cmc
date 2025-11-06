package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：可以文本人工翻译的语言列表
 * 修改历史：
 */
public class TransTextLangBean {
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
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
