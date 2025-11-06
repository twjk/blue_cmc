package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CapOcrBean {
	private String langCode;
	private Long proxyId;
	private String ocrCode;
	public CapOcrBean() {
		super();
	}
	public CapOcrBean(String langCode, Long proxyId, String ocrCode) {
		super();
		this.langCode = langCode;
		this.proxyId = proxyId;
		this.ocrCode = ocrCode;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
	public String getOcrCode() {
		return ocrCode;
	}
	public void setOcrCode(String ocrCode) {
		this.ocrCode = ocrCode;
	}
}