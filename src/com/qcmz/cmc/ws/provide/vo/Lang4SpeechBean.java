package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class Lang4SpeechBean {
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
	 * 是否支持语音识别
	 */
	private boolean asr;
	/**
	 * 是否支持语音合成
	 */
	private boolean tts;
	/**
	 * 是否支持图像识别
	 */
	private boolean ocr;
	
	public Lang4SpeechBean() {
		super();
	}
	
	public Lang4SpeechBean(String langCode, String langCode3, String langName, String language, String langNameLc) {
		super();
		this.langCode = langCode;
		this.langCode3 = langCode3;
		this.langName = langName;
		this.language = language;
		this.langNameLc = langNameLc;
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
	public boolean isAsr() {
		return asr;
	}
	public void setAsr(boolean asr) {
		this.asr = asr;
	}
	public boolean isTts() {
		return tts;
	}
	public void setTts(boolean tts) {
		this.tts = tts;
	}
	public boolean isOcr() {
		return ocr;
	}
	public void setOcr(boolean ocr) {
		this.ocr = ocr;
	}
}
