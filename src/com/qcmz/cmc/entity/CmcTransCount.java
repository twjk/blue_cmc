package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransCount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTransCount implements java.io.Serializable {

	// Fields

	private Long id;
	private String srclang;
	private String tolang;
	private String useridentify;
	private String country;
	private Date createtime;
	private Integer transcount;

	// Constructors

	/** default constructor */
	public CmcTransCount() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrclang() {
		return this.srclang;
	}

	public void setSrclang(String srclang) {
		this.srclang = srclang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public String getUseridentify() {
		return useridentify;
	}

	public void setUseridentify(String useridentify) {
		this.useridentify = useridentify;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getTranscount() {
		return this.transcount;
	}

	public void setTranscount(Integer transcount) {
		this.transcount = transcount;
	}

}