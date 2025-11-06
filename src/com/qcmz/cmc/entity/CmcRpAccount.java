package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;




/**
 * CmcRpAccount entity. @author MyEclipse Persistence Tools
 */

public class CmcRpAccount implements java.io.Serializable {

	// Fields

	private Long accountid;
	private Long userid;

	private UserSimpleBean user;//隔外加的用户信息
	
	private String wxopenid;
	private Double balance = 0D;
	private Double applyamount = 0D;
	private Double cashamount = 0D;
	private Double receiveamount = 0D;
	private Integer receivenum = 0;
	private Double sendamount = 0D;
	private Integer sendnum = 0;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcRpAccount() {
	}

	// Property accessors

	public Long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getWxopenid() {
		return this.wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getApplyamount() {
		return this.applyamount;
	}

	public void setApplyamount(Double applyamount) {
		this.applyamount = applyamount;
	}

	public Double getCashamount() {
		return this.cashamount;
	}

	public void setCashamount(Double cashamount) {
		this.cashamount = cashamount;
	}

	public Double getReceiveamount() {
		return this.receiveamount;
	}

	public void setReceiveamount(Double receiveamount) {
		this.receiveamount = receiveamount;
	}
	
	public Integer getReceivenum() {
		return receivenum;
	}

	public void setReceivenum(Integer receivenum) {
		this.receivenum = receivenum;
	}

	public Double getSendamount() {
		return this.sendamount;
	}

	public void setSendamount(Double sendamount) {
		this.sendamount = sendamount;
	}

	public Integer getSendnum() {
		return this.sendnum;
	}

	public void setSendnum(Integer sendnum) {
		this.sendnum = sendnum;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

}