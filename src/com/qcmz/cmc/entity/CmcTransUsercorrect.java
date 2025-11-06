package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransUsercorrect entity. @author MyEclipse Persistence Tools
 */

public class CmcTransUsercorrect implements java.io.Serializable {

	// Fields

	private Long correctid;
	private String sessionid;
	private Long userid;
	private String uuid;
	private String pushregid;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private String mtdst;
	private Date createtime;
	private Date opertime;
	private String opercd;
	private String opername;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcTransUsercorrect() {
	}

	// Property accessors

	public Long getCorrectid() {
		return this.correctid;
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

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getOpertime() {
		return this.opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}