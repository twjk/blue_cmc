package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CapTtsBean {
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	/**
	 * 代理语言代码
	 */
	private String proxyLangCode;
	/**
	 * 男声
	 */
	private String maleVoice;
	/**
	 * 女声
	 */
	private String femaleVoice;
	/**
	 * 是否收费，0免费1收费
	 */
	private Integer charge;
	public CapTtsBean() {
		super();
	}
	public CapTtsBean(String langCode, Long proxyId, String proxyLangCode,
			String maleVoice, String femaleVoice, Integer charge) {
		super();
		this.langCode = langCode;
		this.proxyId = proxyId;
		this.proxyLangCode = proxyLangCode;
		this.maleVoice = maleVoice;
		this.femaleVoice = femaleVoice;
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
	public String getProxyLangCode() {
		return proxyLangCode;
	}
	public void setProxyLangCode(String proxyLangCode) {
		this.proxyLangCode = proxyLangCode;
	}
	public String getMaleVoice() {
		return maleVoice;
	}
	public void setMaleVoice(String maleVoice) {
		this.maleVoice = maleVoice;
	}
	public String getFemaleVoice() {
		return femaleVoice;
	}
	public void setFemaleVoice(String femaleVoice) {
		this.femaleVoice = femaleVoice;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
}