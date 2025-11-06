package com.qcmz.cmc.ws.provide.vo;

public class LangBean {
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 语言三字码
	 */
	private String langCode3;
	/**
	 * 语言名称
	 */
	private String langName;
	/**
	 * 语言名称对应的语言
	 */
	private String language;
	/**
	 * 语言当地名称
	 */
	private String langNameLc;
	/**
	 * 语言图标
	 */
	private String langIcon;
	/**
	 * 状态
	 */
	private Integer status;
	public LangBean() {
		super();
	}
	public LangBean(String langCode, String langCode3, String langName, String language,
			String langNameLc, String langIcon, Integer status) {
		super();
		this.langCode = langCode;
		this.langCode3 = langCode3;
		this.langName = langName;
		this.language = language;
		this.langNameLc = langNameLc;
		this.langIcon = langIcon;
		this.status = status;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getLangCode3() {
		return langCode3;
	}
	public void setLangCode3(String langCode3) {
		this.langCode3 = langCode3;
	}
	public String getLangName() {
		return langName;
	}
	public void setLangName(String langName) {
		this.langName = langName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLangNameLc() {
		return langNameLc;
	}
	public void setLangNameLc(String langNameLc) {
		this.langNameLc = langNameLc;
	}
	public String getLangIcon() {
		return langIcon;
	}
	public void setLangIcon(String langIcon) {
		this.langIcon = langIcon;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
