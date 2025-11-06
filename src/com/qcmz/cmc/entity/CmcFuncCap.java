package com.qcmz.cmc.entity;

/**
 * CmcFuncCap entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcFuncCap implements java.io.Serializable {
	// Fields

	private Long capid;
	private Long funcid;
	private Long proxyid;
	private String fromlang;
	private String tolang;
	private Integer priority;
	private Integer charge = 0;

	// Constructors

	/** default constructor */
	public CmcFuncCap() {
	}

	// Property accessors

	public Long getFuncid() {
		return funcid;
	}

	public void setFuncid(Long funcid) {
		this.funcid = funcid;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Long getCapid() {
		return capid;
	}

	public void setCapid(Long capid) {
		this.capid = capid;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}
}