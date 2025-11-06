package com.qcmz.cmc.ws.provide.vo;

public class DaySentenceLangBean {
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 源语言名称
	 */
	private String fromName;
	/**
	 * 推送标签
	 */
	private String pushTag;
	
	public DaySentenceLangBean() {
		super();
	}
	
	public DaySentenceLangBean(String from, String fromName, String pushTag) {
		super();
		this.from = from;
		this.fromName = fromName;
		this.pushTag = pushTag;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getPushTag() {
		return pushTag;
	}
	public void setPushTag(String pushTag) {
		this.pushTag = pushTag;
	}
}
