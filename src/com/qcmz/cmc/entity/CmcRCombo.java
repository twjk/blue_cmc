package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRCombo entity. @author MyEclipse Persistence Tools
 */

public class CmcRCombo implements java.io.Serializable {

	// Fields

	private Long id;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long userid;
	private Long comboid;
	private Long pkgid;
	private String title;
	private Integer num;
	private Integer unit;
	private Date starttime;
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcRCombo() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getComboid() {
		return this.comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public Long getPkgid() {
		return this.pkgid;
	}

	public void setPkgid(Long pkgid) {
		this.pkgid = pkgid;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getUnit() {
		return this.unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}