package com.qcmz.cmc.entity;

/**
 * CmcLangIntl entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcLangIntl implements java.io.Serializable {

	// Fields

	private Long intlid;
	private Long langid;
	private CmcLang cmcLang;
	private String langcode;
	private String langname;
	private String language;

	// Constructors

	/** default constructor */
	public CmcLangIntl() {
	}
	// Property accessors

	public Long getIntlid() {
		return this.intlid;
	}

	public void setIntlid(Long intlid) {
		this.intlid = intlid;
	}

	public Long getLangid() {
		return langid;
	}

	public void setLangid(Long langid) {
		this.langid = langid;
	}

	public CmcLang getCmcLang() {
		return cmcLang;
	}

	public void setCmcLang(CmcLang cmcLang) {
		this.cmcLang = cmcLang;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getLangname() {
		return this.langname;
	}

	public void setLangname(String langname) {
		this.langname = langname;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}