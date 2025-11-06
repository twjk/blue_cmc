package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRPackage entity. @author MyEclipse Persistence Tools
 */

public class CmcRPackage implements java.io.Serializable {

	// Fields

	private Long pkgid;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long userid;
	private Long actid;
	private String acttitle;
	private String actdesc;
	private String exchangecode;
	private Integer exchangestatus;
	private Long exchangeuserid;
	private Date exchangetime;
	private Date starttime;

	private UserSimpleBean user;
	private UserSimpleBean exchangeUser;
	
	// Constructors

	/** default constructor */
	public CmcRPackage() {
	}

	// Property accessors

	public Long getPkgid() {
		return this.pkgid;
	}

	public void setPkgid(Long pkgid) {
		this.pkgid = pkgid;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public CmcROrder getCmcROrder() {
		return cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getActid() {
		return this.actid;
	}

	public void setActid(Long actid) {
		this.actid = actid;
	}

	public String getActtitle() {
		return this.acttitle;
	}

	public void setActtitle(String acttitle) {
		this.acttitle = acttitle;
	}

	public String getActdesc() {
		return actdesc;
	}

	public void setActdesc(String actdesc) {
		this.actdesc = actdesc;
	}

	public String getExchangecode() {
		return this.exchangecode;
	}

	public void setExchangecode(String exchangecode) {
		this.exchangecode = exchangecode;
	}

	public Integer getExchangestatus() {
		return this.exchangestatus;
	}

	public void setExchangestatus(Integer exchangestatus) {
		this.exchangestatus = exchangestatus;
	}

	public Long getExchangeuserid() {
		return this.exchangeuserid;
	}

	public void setExchangeuserid(Long exchangeuserid) {
		this.exchangeuserid = exchangeuserid;
	}

	public Date getExchangetime() {
		return this.exchangetime;
	}

	public void setExchangetime(Date exchangetime) {
		this.exchangetime = exchangetime;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public UserSimpleBean getExchangeUser() {
		return exchangeUser;
	}

	public void setExchangeUser(UserSimpleBean exchangeUser) {
		this.exchangeUser = exchangeUser;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

}