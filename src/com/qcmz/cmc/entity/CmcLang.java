package com.qcmz.cmc.entity;

/**
 * CmcLang entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcLang implements java.io.Serializable {

	// Fields

	private Long langid;
	private String langtype;
	private String langcode;
	private String langcode3;
	private String langcn;
	private String langlc;
	private String langicon;
	private Integer sortindex;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcLang() {
	}

	// Property accessors

	public Long getLangid() {
		return this.langid;
	}

	public void setLangid(Long langid) {
		this.langid = langid;
	}

	public String getLangtype() {
		return langtype;
	}

	public void setLangtype(String langtype) {
		this.langtype = langtype;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getLangcode3() {
		return langcode3;
	}

	public void setLangcode3(String langcode3) {
		this.langcode3 = langcode3;
	}

	public String getLangcn() {
		return this.langcn;
	}

	public void setLangcn(String langcn) {
		this.langcn = langcn;
	}

	public String getLanglc() {
		return langlc;
	}

	public void setLanglc(String langlc) {
		this.langlc = langlc;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLangicon() {
		return langicon;
	}

	public void setLangicon(String langicon) {
		this.langicon = langicon;
	}
}