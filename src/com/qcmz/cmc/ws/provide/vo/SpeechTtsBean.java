package com.qcmz.cmc.ws.provide.vo;

public class SpeechTtsBean {
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 合成文本
	 */
	private String text;
	/**
	 * 性别，M男F女
	 */
	private String gender;
	/**
	 * 语速，100为正常
	 */
	private Integer speed;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
}
