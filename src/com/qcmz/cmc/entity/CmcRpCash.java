package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRpCash entity. @author MyEclipse Persistence Tools
 */

public class CmcRpCash implements java.io.Serializable {

	// Fields

	private Long cashid;
	private Long userid;
	private Double cashamount;
	private Date createtime;
	private String outtradeid;
	private Date cashtime;
	private String dealresult;
	private Date dealtime;
	private String dealcd;
	private String dealname;
	private Integer status;
	
	private CmcRpAccount account;
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcRpCash() {
	}

	// Property accessors

	public Long getCashid() {
		return this.cashid;
	}

	public void setCashid(Long cashid) {
		this.cashid = cashid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Double getCashamount() {
		return this.cashamount;
	}

	public void setCashamount(Double cashamount) {
		this.cashamount = cashamount;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCashtime() {
		return cashtime;
	}

	public void setCashtime(Date cashtime) {
		this.cashtime = cashtime;
	}

	public String getOuttradeid() {
		return this.outtradeid;
	}

	public void setOuttradeid(String outtradeid) {
		this.outtradeid = outtradeid;
	}

	public String getDealresult() {
		return this.dealresult;
	}

	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}

	public Date getDealtime() {
		return this.dealtime;
	}

	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}

	public String getDealcd() {
		return this.dealcd;
	}

	public void setDealcd(String dealcd) {
		this.dealcd = dealcd;
	}

	public String getDealname() {
		return this.dealname;
	}

	public void setDealname(String dealname) {
		this.dealname = dealname;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public CmcRpAccount getAccount() {
		return account;
	}

	public void setAccount(CmcRpAccount account) {
		this.account = account;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

}