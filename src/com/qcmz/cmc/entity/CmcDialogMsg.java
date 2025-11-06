package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcDialogMsg entity. @author MyEclipse Persistence Tools
 */

public class CmcDialogMsg implements java.io.Serializable {

	// Fields

	private Long msgid;
	private Long dialogid;
	private CmcDialog cmcDialog;
	private Long userid;
	private String msg;
	private Integer msgside;
	private String msgcd;
	private String msgname;
	private Date createtime;
	
	private UserSimpleBean user;
	
	// Constructors

	/** default constructor */
	public CmcDialogMsg() {
	}

	// Property accessors

	public Long getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getDialogid() {
		return dialogid;
	}

	public void setDialogid(Long dialogid) {
		this.dialogid = dialogid;
	}

	public CmcDialog getCmcDialog() {
		return cmcDialog;
	}

	public void setCmcDialog(CmcDialog cmcDialog) {
		this.cmcDialog = cmcDialog;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getMsgside() {
		return msgside;
	}

	public void setMsgside(Integer msgside) {
		this.msgside = msgside;
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

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}