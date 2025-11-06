package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTtTranshis entity. @author MyEclipse Persistence Tools
 */

public class CmcTtTranshis implements java.io.Serializable {

	// Fields

	private Long hisid;
	private String orderid;
	private String vercode;
	private String dst;
	private String transcd;
	private String transname;
	private Date transtime;
	private String opercd;
	private String opername;
	private Date opertime;
	private Integer usedstatus;

	// Constructors

	/** default constructor */
	public CmcTtTranshis() {
	}

	// Property accessors

	public Long getHisid() {
		return this.hisid;
	}

	public void setHisid(Long hisid) {
		this.hisid = hisid;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getVercode() {
		return this.vercode;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getTranscd() {
		return this.transcd;
	}

	public void setTranscd(String transcd) {
		this.transcd = transcd;
	}

	public String getTransname() {
		return this.transname;
	}

	public void setTransname(String transname) {
		this.transname = transname;
	}

	public Date getTranstime() {
		return this.transtime;
	}

	public void setTranstime(Date transtime) {
		this.transtime = transtime;
	}

	public String getOpercd() {
		return this.opercd;
	}

	public void setOpercd(String opercd) {
		this.opercd = opercd;
	}

	public String getOpername() {
		return this.opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public Date getOpertime() {
		return this.opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public Integer getUsedstatus() {
		return usedstatus;
	}

	public void setUsedstatus(Integer usedstatus) {
		this.usedstatus = usedstatus;
	}
}