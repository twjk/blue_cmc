package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CapAsrBean {
	private String langCode;
	private Long proxyId;
	private String asrCode;
	private Integer charge;
	public CapAsrBean() {
		super();
	}
	public CapAsrBean(String langCode, Long proxyId, String asrCode, Integer charge) {
		super();
		this.langCode = langCode;
		this.proxyId = proxyId;
		this.asrCode = asrCode;
		this.charge = charge;
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
	public String getAsrCode() {
		return asrCode;
	}
	public void setAsrCode(String asrCode) {
		this.asrCode = asrCode;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
}