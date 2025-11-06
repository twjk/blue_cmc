package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcWalletRecharge entity. @author MyEclipse Persistence Tools
 */

public class CmcWalletRecharge implements java.io.Serializable {

	// Fields

	private String orderid;
	private Long userid;
	private CmcWalletAccount cmcWalletAccount;
	private Double amount;
	private Double payableamount;
	private Double payamount = 0.0;
	private String iapid;
	private String tradeway;
	private Date paytime;
	private String payouttradeid;
	private String payplatform;
	private Date refundtime;
	private String refundouttradeid;
	private String platform;
	private String version;
	private Date createtime;
	private Integer status;
	
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcWalletRecharge() {
	}

	// Property accessors

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public CmcWalletAccount getCmcWalletAccount() {
		return this.cmcWalletAccount;
	}

	public void setCmcWalletAccount(CmcWalletAccount cmcWalletAccount) {
		this.cmcWalletAccount = cmcWalletAccount;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPayableamount() {
		return this.payableamount;
	}

	public void setPayableamount(Double payableamount) {
		this.payableamount = payableamount;
	}

	public Double getPayamount() {
		return this.payamount;
	}

	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}

	public String getTradeway() {
		return this.tradeway;
	}

	public void setTradeway(String tradeway) {
		this.tradeway = tradeway;
	}

	public String getPayouttradeid() {
		return payouttradeid;
	}

	public void setPayouttradeid(String payouttradeid) {
		this.payouttradeid = payouttradeid;
	}

	public Date getRefundtime() {
		return refundtime;
	}

	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}

	public String getRefundouttradeid() {
		return refundouttradeid;
	}

	public void setRefundouttradeid(String refundouttradeid) {
		this.refundouttradeid = refundouttradeid;
	}

	public Date getPaytime() {
		return this.paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public String getPayplatform() {
		return this.payplatform;
	}

	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public String getIapid() {
		return iapid;
	}

	public void setIapid(String iapid) {
		this.iapid = iapid;
	}
}