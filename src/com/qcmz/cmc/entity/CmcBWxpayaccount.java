package com.qcmz.cmc.entity;

/**
 * UmcBWxpayaccount entity. @author MyEclipse Persistence Tools
 */

public class CmcBWxpayaccount implements java.io.Serializable {

	// Fields

	private Long accountid;
	private String servicetype;
	private String subservicetype;
	private String platform;
	private String appid;
	private String mchid;
	private String apikey;
	private String cert;
	private String tradetype;
	private String notifyurl;
	private String remark;

	// Constructors

	/** default constructor */
	public CmcBWxpayaccount() {
	}

	// Property accessors

	public Long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public String getServicetype() {
		return this.servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getSubservicetype() {
		return this.subservicetype;
	}

	public void setSubservicetype(String subservicetype) {
		this.subservicetype = subservicetype;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchid() {
		return this.mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getCert() {
		return this.cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getTradetype() {
		return this.tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getNotifyurl() {
		return this.notifyurl;
	}

	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}