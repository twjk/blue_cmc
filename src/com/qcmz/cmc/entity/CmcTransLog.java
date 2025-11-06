package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransLog entity. @author MyEclipse Persistence Tools
 */

public class CmcTransLog implements java.io.Serializable {

	// Fields

	private Long logid;
	private String token;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private Integer similar;
	private Date starttime;
	private Date endtime;
	private Integer succ;
	private String transresult;
	private Long proxyid;
	private String reqip;
	private String reqcountry;
	private String reqcity;
	private String lon;
	private String lat;
	private String platform;
	private String appversion;

	// Constructors

	/** default constructor */
	public CmcTransLog() {
	}

	// Property accessors
	public Long getLogid() {
		return this.logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getSucc() {
		return this.succ;
	}

	public void setSucc(Integer succ) {
		this.succ = succ;
	}

	public String getTransresult() {
		return this.transresult;
	}

	public void setTransresult(String transresult) {
		this.transresult = transresult;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public String getReqip() {
		return this.reqip;
	}

	public void setReqip(String reqip) {
		this.reqip = reqip;
	}

	public String getReqcountry() {
		return this.reqcountry;
	}

	public void setReqcountry(String reqcountry) {
		this.reqcountry = reqcountry;
	}

	public String getReqcity() {
		return this.reqcity;
	}

	public void setReqcity(String reqcity) {
		this.reqcity = reqcity;
	}

	public String getLon() {
		return this.lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAppversion() {
		return this.appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

}