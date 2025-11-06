package com.qcmz.cmc.entity;

/**
 * CmcComboPic entity. @author MyEclipse Persistence Tools
 */

public class CmcComboPic implements java.io.Serializable {

	// Fields

	private Long picid;
	private Long comboid;
	private CmcCombo cmcCombo;
	private String picurl;
	private Integer sortindex;

	// Constructors

	/** default constructor */
	public CmcComboPic() {
	}

	// Property accessors

	public Long getPicid() {
		return this.picid;
	}

	public void setPicid(Long picid) {
		this.picid = picid;
	}

	public Long getComboid() {
		return comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public CmcCombo getCmcCombo() {
		return this.cmcCombo;
	}

	public void setCmcCombo(CmcCombo cmcCombo) {
		this.cmcCombo = cmcCombo;
	}

	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

}