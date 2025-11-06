package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcUCombohis entity. @author MyEclipse Persistence Tools
 */

public class CmcUCombohis implements java.io.Serializable {

	// Fields

	private Long hisid;
	private Long ucid;
	private CmcUCombo cmcUCombo;
	private String usedorderid;
	private Date usedtime;
	private String remark;
	private String opercd;
	private String opername;
	private Date opertime;

	// Constructors

	/** default constructor */
	public CmcUCombohis() {
	}

	// Property accessors

	public Long getHisid() {
		return this.hisid;
	}

	public void setHisid(Long hisid) {
		this.hisid = hisid;
	}

	public Long getUcid() {
		return ucid;
	}

	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}

	public CmcUCombo getCmcUCombo() {
		return this.cmcUCombo;
	}

	public void setCmcUCombo(CmcUCombo cmcUCombo) {
		this.cmcUCombo = cmcUCombo;
	}

	public String getUsedorderid() {
		return this.usedorderid;
	}

	public void setUsedorderid(String usedorderid) {
		this.usedorderid = usedorderid;
	}

	public Date getUsedtime() {
		return this.usedtime;
	}

	public void setUsedtime(Date usedtime) {
		this.usedtime = usedtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpercd() {
		return opercd;
	}

	public void setOpercd(String opercd) {
		this.opercd = opercd;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
}