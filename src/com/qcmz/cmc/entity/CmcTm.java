package com.qcmz.cmc.entity;

import com.qcmz.framework.util.log.BeanDesc;

/**
 * CmcTm entity. @author MyEclipse Persistence Tools
 */

public class CmcTm implements java.io.Serializable {

	// Fields
	@BeanDesc(desc="翻译机ID")
	private Long tmid;
	@BeanDesc(desc="翻译机编码")
	private String tmcode;

	// Constructors

	/** default constructor */
	public CmcTm() {
	}

	/** full constructor */
	public CmcTm(String tmcode) {
		this.tmcode = tmcode;
	}

	// Property accessors

	public Long getTmid() {
		return this.tmid;
	}

	public void setTmid(Long tmid) {
		this.tmid = tmid;
	}

	public String getTmcode() {
		return this.tmcode;
	}

	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}

}