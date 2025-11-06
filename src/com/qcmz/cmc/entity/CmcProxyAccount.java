package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcProxyAccount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcProxyAccount implements java.io.Serializable {

	// Fields

	private Long accountid;
	private Long proxyid;
	private String account;
	private String key1;
	private String key2;
	private String serverurl;
	private String serverport;
	private String region;
	private String platform;
	private Integer scope;
	private Date regdate;
	private Date restartdate;
	private Long quota;
	private Long called;
	private Integer status;
	private String description;

	// Constructors

	/** default constructor */
	public CmcProxyAccount() {
	}

	// Property accessors

	public Long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getKey1() {
		return this.key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return this.key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getServerurl() {
		return this.serverurl;
	}

	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}

	public String getServerport() {
		return this.serverport;
	}

	public void setServerport(String serverport) {
		this.serverport = serverport;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public Date getRegdate() {
		return this.regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getRestartdate() {
		return this.restartdate;
	}

	public void setRestartdate(Date restartdate) {
		this.restartdate = restartdate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getQuota() {
		return quota;
	}

	public void setQuota(Long quota) {
		this.quota = quota;
	}

	public Long getCalled() {
		return called;
	}

	public void setCalled(Long called) {
		this.called = called;
	}

}