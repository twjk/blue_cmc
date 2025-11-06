package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcPtTranshis entity. @author MyEclipse Persistence Tools
 */

public class CmcPtTranshis implements java.io.Serializable {

	// Fields

	private Long hisid;
	private String picid;
	private String vercode;
	private String dst;
	private Integer posx;
	private Integer posy;
	private String transcd;
	private String transname;
	private Date transtime;
	private String opercd;
	private String opername;
	private Date opertime;

	// Constructors

	/** default constructor */
	public CmcPtTranshis() {
	}

	// Property accessors
	public String getPicid() {
		return picid;
	}

	public Long getHisid() {
		return hisid;
	}

	public void setHisid(Long hisid) {
		this.hisid = hisid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getVercode() {
		return vercode;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
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

	public String getTranscd() {
		return this.transcd;
	}

	public void setTranscd(String transcd) {
		this.transcd = transcd;
	}

	public String getTransname() {
		return this.transname;
	}

	public void setTransname(String transname) {
		this.transname = transname;
	}

	public Date getTranstime() {
		return this.transtime;
	}

	public void setTranstime(Date transtime) {
		this.transtime = transtime;
	}

	public String getOpercd() {
		return this.opercd;
	}

	public void setOpercd(String opercd) {
		this.opercd = opercd;
	}

	public String getOpername() {
		return this.opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public Date getOpertime() {
		return this.opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

}