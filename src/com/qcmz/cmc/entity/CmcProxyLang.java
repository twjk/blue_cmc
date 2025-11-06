package com.qcmz.cmc.entity;

/**
 * CmcProxyLang entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcProxyLang implements java.io.Serializable {

	// Fields

	private Long id;
	private Long proxyid;
	private String langcode;
	private String mtcode;
	private String asrcode;
	private String ttscode;
	private String ttsmalevoice;
	private String ttsfemalevoice;
	private String ocrcode;
	
	// Constructors

	/** default constructor */
	public CmcProxyLang() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getMtcode() {
		return mtcode;
	}

	public void setMtcode(String mtcode) {
		this.mtcode = mtcode;
	}

	public String getAsrcode() {
		return this.asrcode;
	}

	public void setAsrcode(String asrcode) {
		this.asrcode = asrcode;
	}

	public String getTtscode() {
		return ttscode;
	}

	public void setTtscode(String ttscode) {
		this.ttscode = ttscode;
	}

	public String getTtsmalevoice() {
		return this.ttsmalevoice;
	}

	public void setTtsmalevoice(String ttsmalevoice) {
		this.ttsmalevoice = ttsmalevoice;
	}

	public String getTtsfemalevoice() {
		return this.ttsfemalevoice;
	}

	public void setTtsfemalevoice(String ttsfemalevoice) {
		this.ttsfemalevoice = ttsfemalevoice;
	}

	public String getOcrcode() {
		return ocrcode;
	}

	public void setOcrcode(String ocrcode) {
		this.ocrcode = ocrcode;
	}
}