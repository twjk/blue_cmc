package com.qcmz.cmc.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * CmcFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcFunction implements java.io.Serializable {

	// Fields

	private Long funcid;
	private String funcname;
	private Set cmcProxyFuncs = new HashSet(0);

	// Constructors

	/** default constructor */
	public CmcFunction() {
	}

	/** minimal constructor */
	public CmcFunction(String funcname) {
		this.funcname = funcname;
	}

	/** full constructor */
	public CmcFunction(String funcname, Set cmcProxyFuncs) {
		this.funcname = funcname;
		this.cmcProxyFuncs = cmcProxyFuncs;
	}

	// Property accessors

	public Long getFuncid() {
		return this.funcid;
	}

	public void setFuncid(Long funcid) {
		this.funcid = funcid;
	}

	public String getFuncname() {
		return this.funcname;
	}

	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}

	public Set getCmcProxyFuncs() {
		return this.cmcProxyFuncs;
	}

	public void setCmcProxyFuncs(Set cmcProxyFuncs) {
		this.cmcProxyFuncs = cmcProxyFuncs;
	}

}