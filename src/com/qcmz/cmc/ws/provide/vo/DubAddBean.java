package com.qcmz.cmc.ws.provide.vo;

public class DubAddBean extends OrderCreateBean{
	/**
	 * 名称
	 */
	private String title;
	/**
	 * 文本
	 */
	private String txt;
	/**
	 * 配音员编号
	 */
	private Long dubberId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public Long getDubberId() {
		return dubberId;
	}
	public void setDubberId(Long dubberId) {
		this.dubberId = dubberId;
	}
}