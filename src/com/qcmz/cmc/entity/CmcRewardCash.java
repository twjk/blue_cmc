package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRewardCash entity. @author MyEclipse Persistence Tools
 */

public class CmcRewardCash implements java.io.Serializable {

	// Fields

	private Long cashid;
	private Long userid;
	private CmcRewardAccount cmcRewardAccount;
	private Double cashamount;
	private Date createtime;
	private String accounttype;
	private String account;
	private String outtradeid;
	private String traderesult;
	private Date cashtime;
	private String dealresult;
	private Date dealtime;
	private String dealcd;
	private String dealname;
	private Integer status;

	private UserSimpleBean user;
	
	// Constructors

	/** default constructor */
	public CmcRewardCash() {
	}

	// Property accessors

	public Long getCashid() {
		return this.cashid;
	}

	public void setCashid(Long cashid) {
		this.cashid = cashid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public CmcRewardAccount getCmcRewardAccount() {
		return this.cmcRewardAccount;
	}

	public void setCmcRewardAccount(CmcRewardAccount cmcRewardAccount) {
		this.cmcRewardAccount = cmcRewardAccount;
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

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOuttradeid() {
		return this.outtradeid;
	}

	public void setOuttradeid(String outtradeid) {
		this.outtradeid = outtradeid;
	}

	public String getTraderesult() {
		return traderesult;
	}

	public void setTraderesult(String traderesult) {
		this.traderesult = traderesult;
	}

	public Date getCashtime() {
		return this.cashtime;
	}

	public void setCashtime(Date cashtime) {
		this.cashtime = cashtime;
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

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

}