package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRMsg entity. @author MyEclipse Persistence Tools
 */

public class CmcRMsg implements java.io.Serializable {

	// Fields

	private Long msgid;
	private String orderid;
	private CmcROrder cmcROrder;
	private String msg;
	private String sidetype;
	private String msgcd;
	private String msgname;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcRMsg() {
	}

	// Property accessors

	public Long getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public CmcROrder getCmcROrder() {
		return this.cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSidetype() {
		return this.sidetype;
	}

	public void setSidetype(String sidetype) {
		this.sidetype = sidetype;
	}

	public String getMsgcd() {
		return this.msgcd;
	}

	public void setMsgcd(String msgcd) {
		this.msgcd = msgcd;
	}

	public String getMsgname() {
		return this.msgname;
	}

	public void setMsgname(String msgname) {
		this.msgname = msgname;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}