package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcREval entity. @author MyEclipse Persistence Tools
 */

public class CmcREval implements java.io.Serializable {

	// Fields

	private Long revalid;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long evalid;
	private Integer evallevel;
	private String levelname;
	private String evaltag;
	private Date evaltime;

	// Constructors

	/** default constructor */
	public CmcREval() {
	}

	// Property accessors

	public Long getRevalid() {
		return this.revalid;
	}

	public void setRevalid(Long revalid) {
		this.revalid = revalid;
	}

	public CmcROrder getCmcROrder() {
		return this.cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public Long getEvalid() {
		return evalid;
	}

	public void setEvalid(Long evalid) {
		this.evalid = evalid;
	}

	public Integer getEvallevel() {
		return this.evallevel;
	}

	public void setEvallevel(Integer evallevel) {
		this.evallevel = evallevel;
	}

	public String getLevelname() {
		return this.levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getEvaltag() {
		return this.evaltag;
	}

	public void setEvaltag(String evaltag) {
		this.evaltag = evaltag;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Date getEvaltime() {
		return evaltime;
	}

	public void setEvaltime(Date evaltime) {
		this.evaltime = evaltime;
	}
}