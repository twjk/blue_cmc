package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRLog entity. @author MyEclipse Persistence Tools
 */

public class CmcRLog implements java.io.Serializable {

	// Fields

	private Long logid;
	private String orderid;
	private CmcROrder cmcROrder;
	private String dealstatus;
	private String logtype;
	private String log;
	private Date opertime;
	private String opercd;
	private String opername;

	// Constructors

	/** default constructor */
	public CmcRLog() {
	}

	// Property accessors

	public Long getLogid() {
		return this.logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public CmcROrder getCmcROrder() {
		return this.cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public String getDealstatus() {
		return this.dealstatus;
	}

	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}

	public String getLogtype() {
		return this.logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
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

}