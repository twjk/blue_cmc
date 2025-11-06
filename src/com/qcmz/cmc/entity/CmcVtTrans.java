package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcVtTrans entity. @author MyEclipse Persistence Tools
 */

public class CmcVtTrans implements java.io.Serializable {

	// Fields

	private Long vtid;
	private String orderid;
	private CmcROrder cmcROrder;
	private CmcWalletAccount cmcWalletAccount;
	private Long userid;
	private String fromlang;
	private String tolang;
	private Long sceneid;
	private CmcBScene cmcBScene;
	private String roomid;
	private Integer calltype;
	private Date connecttime;
	private Date startbillingtime;
	private Date handuptime;
	private Integer connectduration;
	private Integer billingduration;
	
	private Integer billingMinute;
	private UserSimpleBean user;	

	// Constructors

	/** default constructor */
	public CmcVtTrans() {
	}

	// Property accessors

	public Long getVtid() {
		return this.vtid;
	}

	public void setVtid(Long vtid) {
		this.vtid = vtid;
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

	public CmcWalletAccount getCmcWalletAccount() {
		return cmcWalletAccount;
	}

	public void setCmcWalletAccount(CmcWalletAccount cmcWalletAccount) {
		this.cmcWalletAccount = cmcWalletAccount;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public Long getSceneid() {
		return sceneid;
	}

	public void setSceneid(Long sceneid) {
		this.sceneid = sceneid;
	}

	public CmcBScene getCmcBScene() {
		return cmcBScene;
	}

	public void setCmcBScene(CmcBScene cmcBScene) {
		this.cmcBScene = cmcBScene;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public Integer getCalltype() {
		return calltype;
	}

	public void setCalltype(Integer calltype) {
		this.calltype = calltype;
	}

	public Date getConnecttime() {
		return this.connecttime;
	}

	public void setConnecttime(Date connecttime) {
		this.connecttime = connecttime;
	}

	public Date getStartbillingtime() {
		return this.startbillingtime;
	}

	public void setStartbillingtime(Date startbillingtime) {
		this.startbillingtime = startbillingtime;
	}

	public Date getHanduptime() {
		return this.handuptime;
	}

	public void setHanduptime(Date handuptime) {
		this.handuptime = handuptime;
	}

	public Integer getConnectduration() {
		return this.connectduration;
	}

	public void setConnectduration(Integer connectduration) {
		this.connectduration = connectduration;
	}

	public Integer getBillingduration() {
		return this.billingduration;
	}

	public void setBillingduration(Integer billingduration) {
		this.billingduration = billingduration;
	}

	public Integer getBillingMinute() {
		if(billingduration!=null){
			billingMinute = billingduration/60;
			if(billingduration%60>0){
				billingMinute++;
			}
		}
		return billingMinute;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}