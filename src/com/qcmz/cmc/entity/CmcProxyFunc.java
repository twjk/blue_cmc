package com.qcmz.cmc.entity;

/**
 * CmcProxyFunc entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcProxyFunc implements java.io.Serializable {

	// Fields

	private Long id;
	private Long proxyid;
	private CmcProxy cmcProxy;
	private Long funcid;
	private CmcFunction cmcFunction;

	// Constructors

	/** default constructor */
	public CmcProxyFunc() {
	}

	public CmcProxyFunc(Long proxyid, Long funcid) {
		super();
		this.proxyid = proxyid;
		this.funcid = funcid;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncid() {
		return funcid;
	}

	public void setFuncid(Long funcid) {
		this.funcid = funcid;
	}

	public CmcFunction getCmcFunction() {
		return this.cmcFunction;
	}

	public void setCmcFunction(CmcFunction cmcFunction) {
		this.cmcFunction = cmcFunction;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public CmcProxy getCmcProxy() {
		return cmcProxy;
	}

	public void setCmcProxy(CmcProxy cmcProxy) {
		this.cmcProxy = cmcProxy;
	}

}