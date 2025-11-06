package com.qcmz.cmc.proxy.speech.google.vo;

public class GoogleSynthesizeParams {
	/**
	 * 语言代码
	 */
	private String languageCode;
	/**
	 * 发音人名称
	 */
	private String name;
	/**
	 * 性别，FEMALE或MALE
	 */
	private String ssmlGender;
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsmlGender() {
		return ssmlGender;
	}
	public void setSsmlGender(String ssmlGender) {
		this.ssmlGender = ssmlGender;
	}
}
