package com.qcmz.cmc.entity;

/**
 * CmcIrCat entity. @author MyEclipse Persistence Tools
 */

public class CmcIrCat implements java.io.Serializable {

	// Fields

	private Long catid;
	private String catname;
	private Integer charge;
	private String icon;
	private String overview;
	private Integer sortindex;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcIrCat() {
	}

	// Property accessors

	public Long getCatid() {
		return this.catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return this.catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
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
}