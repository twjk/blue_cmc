package com.qcmz.cmc.entity;

/**
 * CmcProxy entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcProxy implements java.io.Serializable {

	// Fields

	private Long proxyid;
	private String proxycode;
	private String proxyname;
	private String busitype;
	private Integer priority;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcProxy() {
	}

	// Property accessors

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public String getProxycode() {
		return this.proxycode;
	}

	public void setProxycode(String proxycode) {
		this.proxycode = proxycode;
	}

	public String getProxyname() {
		return this.proxyname;
	}

	public void setProxyname(String proxyname) {
		this.proxyname = proxyname;
	}

	public String getBusitype() {
		return this.busitype;
	}

	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}