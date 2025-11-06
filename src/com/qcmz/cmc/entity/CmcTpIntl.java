package com.qcmz.cmc.entity;

/**
 * CmcTpIntl entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTpIntl implements java.io.Serializable {

	// Fields
	private Long intlid;
	private Long priceid;
	private CmcTpPrice cmcTpPrice;
	private String overview;
	private String service;
	private String worktimedesc;
	private String language;

	// Constructors

	/** default constructor */
	public CmcTpIntl() {
	}

	public Long getIntlid() {
		return intlid;
	}

	public void setIntlid(Long intlid) {
		this.intlid = intlid;
	}

	public Long getPriceid() {
		return priceid;
	}

	public void setPriceid(Long priceid) {
		this.priceid = priceid;
	}

	public CmcTpPrice getCmcTpPrice() {
		return cmcTpPrice;
	}

	public void setCmcTpPrice(CmcTpPrice cmcTpPrice) {
		this.cmcTpPrice = cmcTpPrice;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getWorktimedesc() {
		return worktimedesc;
	}

	public void setWorktimedesc(String worktimedesc) {
		this.worktimedesc = worktimedesc;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	// Property accessors
	
}