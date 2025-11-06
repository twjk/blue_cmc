package com.qcmz.cmc.entity;

/**
 * CmcBAlipayaccount entity. @author MyEclipse Persistence Tools
 */

public class CmcBAlipayaccount implements java.io.Serializable {

	// Fields

	private Long accountid;
	private String remark;
	private String servicetype;
	private String appid;
	private String publickey;
	private String privatekey;
	private String signtype;
	private String apppubliccert;
	private String alipaypubliccert;
	private String rootcert;
	private String notifyurl;

	// Constructors

	/** default constructor */
	public CmcBAlipayaccount() {
	}

	// Property accessors

	public Long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServicetype() {
		return this.servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPublickey() {
		return this.publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	public String getPrivatekey() {
		return this.privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getSigntype() {
		return this.signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	public String getApppubliccert() {
		return apppubliccert;
	}

	public void setApppubliccert(String apppubliccert) {
		this.apppubliccert = apppubliccert;
	}

	public String getAlipaypubliccert() {
		return alipaypubliccert;
	}

	public void setAlipaypubliccert(String alipaypubliccert) {
		this.alipaypubliccert = alipaypubliccert;
	}

	public String getRootcert() {
		return rootcert;
	}

	public void setRootcert(String rootcert) {
		this.rootcert = rootcert;
	}

	public String getNotifyurl() {
		return this.notifyurl;
	}

	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}

}