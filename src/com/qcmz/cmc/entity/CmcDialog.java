package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcDialog entity. @author MyEclipse Persistence Tools
 */

public class CmcDialog implements java.io.Serializable {

	// Fields

	private Long dialogid;
	private Long userid;
	private Date msgtime;
	private Integer dealstatus;
	
	private UserSimpleBean user;
	private List<CmcDialogMsg> msgs = new ArrayList<CmcDialogMsg>(0);

	// Constructors

	/** default constructor */
	public CmcDialog() {
	}

	// Property accessors

	public Long getDialogid() {
		return this.dialogid;
	}

	public void setDialogid(Long dialogid) {
		this.dialogid = dialogid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getMsgtime() {
		return this.msgtime;
	}

	public void setMsgtime(Date msgtime) {
		this.msgtime = msgtime;
	}

	public Integer getDealstatus() {
		return this.dealstatus;
	}

	public void setDealstatus(Integer dealstatus) {
		this.dealstatus = dealstatus;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public List<CmcDialogMsg> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<CmcDialogMsg> msgs) {
		this.msgs = msgs;
	}
}