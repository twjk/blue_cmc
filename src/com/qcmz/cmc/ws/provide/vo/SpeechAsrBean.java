package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

import com.qcmz.framework.annotation.JsonIgore;

public class SpeechAsrBean {
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 音频文件
	 */
	private File file;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	
	public SpeechAsrBean() {
		super();
	}
	public SpeechAsrBean(String langCode, File file, Long proxyId) {
		super();
		this.langCode = langCode;
		this.file = file;
		this.proxyId = proxyId;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	@JsonIgore
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
}
