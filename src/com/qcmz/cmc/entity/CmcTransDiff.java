package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransDiff entity. @author MyEclipse Persistence Tools
 */

public class CmcTransDiff implements java.io.Serializable {

	// Fields

	private Long diffid;
	private Long proxyid;
	private String sessionid;
	private Long userid;
	private String uuid;
	private String pushregid;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private Integer similar;
	private Date transtime;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcTransDiff() {
	}

	// Property accessors

	public Long getDiffid() {
		return this.diffid;
	}

	public void setDiffid(Long diffid) {
		this.diffid = diffid;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
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

	public Integer getSimilar() {
		return this.similar;
	}

	public void setSimilar(Integer similar) {
		this.similar = similar;
	}

	public Date getTranstime() {
		return this.transtime;
	}

	public void setTranstime(Date transtime) {
		this.transtime = transtime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}