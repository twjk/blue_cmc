package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransDiff entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTransFav implements java.io.Serializable {

	// Fields

	private Long favid;
	private String uid;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private Date favtime;

	// Constructors

	/** default constructor */
	public CmcTransFav() {
	}

	// Property accessors
	public Long getFavid() {
		return favid;
	}

	public void setFavid(Long favid) {
		this.favid = favid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFromlang() {
		return fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTolang() {
		return tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public Date getFavtime() {
		return favtime;
	}

	public void setFavtime(Date favtime) {
		this.favtime = favtime;
	}
}