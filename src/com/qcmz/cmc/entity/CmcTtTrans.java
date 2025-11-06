package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcTtTrans entity. @author MyEclipse Persistence Tools
 */

public class CmcTtTrans implements java.io.Serializable {

	// Fields

	private Long ttid;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long userid;
	private Integer transmodel;
	private String fromlang;
	private String tolang;
	private String src;
	private String dst;
	private String voice;
	private String sessionid;
	private String pushregid;
	private Date createtime;
	private String transcd;
    private String transname;
    private Date transtime;

	private UserSimpleBean user;
	
	// Constructors

	/** default constructor */
	public CmcTtTrans() {
	}

	// Property accessors

	public Long getTtid() {
		return this.ttid;
	}

	public void setTtid(Long ttid) {
		this.ttid = ttid;
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

	public Integer getTransmodel() {
		return this.transmodel;
	}

	public void setTransmodel(Integer transmodel) {
		this.transmodel = transmodel;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getPushregid() {
		return pushregid;
	}

	public void setPushregid(String pushregid) {
		this.pushregid = pushregid;
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

	public String getTranscd() {
		return transcd;
	}

	public void setTranscd(String transcd) {
		this.transcd = transcd;
	}

	public String getTransname() {
		return transname;
	}

	public void setTransname(String transname) {
		this.transname = transname;
	}

	public Date getTranstime() {
		return transtime;
	}

	public void setTranstime(Date transtime) {
		this.transtime = transtime;
	}
}