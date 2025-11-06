package com.qcmz.cmc.entity;

/**
 * CmcTpLang entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTpLang implements java.io.Serializable {

	// Fields

	private Long langid;
	private Long priceid;
	private CmcTpPrice cmcTpPrice;
	private String fromlang;
	private String tolang;

	// Constructors

	/** default constructor */
	public CmcTpLang() {
	}


	// Property accessors

	public Long getLangid() {
		return this.langid;
	}

	public void setLangid(Long langid) {
		this.langid = langid;
	}

	public Long getPriceid() {
		return priceid;
	}


	public void setPriceid(Long priceid) {
		this.priceid = priceid;
	}


	public CmcTpPrice getCmcTpPrice() {
		return this.cmcTpPrice;
	}

	public void setCmcTpPrice(CmcTpPrice cmcTpPrice) {
		this.cmcTpPrice = cmcTpPrice;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

}