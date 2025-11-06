package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRTrade entity. @author MyEclipse Persistence Tools
 */

public class CmcRTrade implements java.io.Serializable {

	// Fields

	private Long tradeid;
	private String orderid;
	private CmcROrder cmcROrder;
	private String tradetype;
	private String tradestatus;
	private String traderesult;
	private Double amount;
	private String outtradeid;
	private Date tradetime;

	// Constructors

	/** default constructor */
	public CmcRTrade() {
	}

	// Property accessors

	public Long getTradeid() {
		return this.tradeid;
	}

	public void setTradeid(Long tradeid) {
		this.tradeid = tradeid;
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

	public String getTradetype() {
		return this.tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getTradestatus() {
		return this.tradestatus;
	}

	public void setTradestatus(String tradestatus) {
		this.tradestatus = tradestatus;
	}

	public String getTraderesult() {
		return this.traderesult;
	}

	public void setTraderesult(String traderesult) {
		this.traderesult = traderesult;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOuttradeid() {
		return this.outtradeid;
	}

	public void setOuttradeid(String outtradeid) {
		this.outtradeid = outtradeid;
	}

	public Date getTradetime() {
		return this.tradetime;
	}

	public void setTradetime(Date tradetime) {
		this.tradetime = tradetime;
	}

}