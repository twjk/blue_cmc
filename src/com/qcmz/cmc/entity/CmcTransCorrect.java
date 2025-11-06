package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransHuman entity. @author MyEclipse Persistence Tools
 */

public class CmcTransCorrect implements java.io.Serializable {

	// Fields

	private Long correctid;
	private String sessionid;
	private Long userid;
	private String uuid;
	private String pushregid;
	private Integer correctsource;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private String mtdst;
	private Date opertime;
	private String opercd;
	private String opername;

	// Constructors

	/** default constructor */
	public CmcTransCorrect() {
	}
	// Property accessors

	public Long getCorrectid() {
		return correctid;
	}

	public void setCorrectid(Long correctid) {
		this.correctid = correctid;
	}

	public String getSessionid() {
		return this.sessionid;
	}
	
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPushregid() {
		return this.pushregid;
	}

	public void setPushregid(String pushregid) {
		this.pushregid = pushregid;
	}

	public Integer getCorrectsource() {
		return correctsource;
	}

	public void setCorrectsource(Integer correctsource) {
		this.correctsource = correctsource;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getMtdst() {
		return this.mtdst;
	}

	public void setMtdst(String mtdst) {
		this.mtdst = mtdst;
	}

	public String getOpercd() {
		return opercd;
	}

	public void setOpercd(String opercd) {
		this.opercd = opercd;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}
}