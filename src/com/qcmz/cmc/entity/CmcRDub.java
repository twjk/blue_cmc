package com.qcmz.cmc.entity;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRDub entity. @author MyEclipse Persistence Tools
 */

public class CmcRDub implements java.io.Serializable {

	// Fields

	private Long id;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long userid;
	private String title;
	private String txt;
	private Long dubberid;
	private CmcDubber cmcDubber;
	private String audio;
	
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcRDub() {
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxt() {
		return this.txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Long getDubberid() {
		return dubberid;
	}

	public void setDubberid(Long dubberid) {
		this.dubberid = dubberid;
	}

	public CmcDubber getCmcDubber() {
		return cmcDubber;
	}

	public void setCmcDubber(CmcDubber cmcDubber) {
		this.cmcDubber = cmcDubber;
	}

	public String getAudio() {
		return this.audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}