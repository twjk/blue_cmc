package com.qcmz.cmc.entity;

/**
 * CmcKeyword entity. @author MyEclipse Persistence Tools
 */

public class CmcKeyword implements java.io.Serializable {

	// Fields

	private Long kwid;
	private Long typeid;
	private String langcode;
	private CmcKeywordType cmcKeywordType;
	private String keyword;
	private Long hitcount;
	private String url;

	// Constructors

	/** default constructor */
	public CmcKeyword() {
	}

	// Property accessors

	public Long getKwid() {
		return this.kwid;
	}

	public void setKwid(Long kwid) {
		this.kwid = kwid;
	}

	public Long getTypeid() {
		return typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public String getLangcode() {
		return langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public CmcKeywordType getCmcKeywordType() {
		return this.cmcKeywordType;
	}

	public void setCmcKeywordType(CmcKeywordType cmcKeywordType) {
		this.cmcKeywordType = cmcKeywordType;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getHitcount() {
		return this.hitcount;
	}

	public void setHitcount(Long hitcount) {
		this.hitcount = hitcount;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}