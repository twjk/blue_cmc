package com.qcmz.cmc.entity;

/**
 * CmcFeeAccount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcFeeAccount implements java.io.Serializable {

	// Fields

	private Long userid;
	private UmcUUser umcUUser;
	private Integer point;
	private Integer adstatus;

	// Constructors

	/** default constructor */
	public CmcFeeAccount() {
	}


	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getAdstatus() {
		return this.adstatus;
	}

	public void setAdstatus(Integer adstatus) {
		this.adstatus = adstatus;
	}


	public UmcUUser getUmcUUser() {
		return umcUUser;
	}


	public void setUmcUUser(UmcUUser umcUUser) {
		this.umcUUser = umcUUser;
	}
}