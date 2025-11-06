package com.qcmz.cmc.entity;

/**
 * CmcPtTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcPtTrans implements java.io.Serializable {

	// Fields

	private Long transid;
	private String picid;
	private String dst;
	private Integer posx;
	private Integer posy;

	// Constructors

	/** default constructor */
	public CmcPtTrans() {
	}

	/** full constructor */
	public CmcPtTrans(String picid, String dst, Integer posx, Integer posy) {
		this.picid = picid;
		this.dst = dst;
		this.posx = posx;
		this.posy = posy;
	}

	// Property accessors

	public Long getTransid() {
		return this.transid;
	}

	public void setTransid(Long transid) {
		this.transid = transid;
	}

	public String getPicid() {
		return this.picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public Integer getPosx() {
		return this.posx;
	}

	public void setPosx(Integer posx) {
		this.posx = posx;
	}

	public Integer getPosy() {
		return this.posy;
	}

	public void setPosy(Integer posy) {
		this.posy = posy;
	}

}