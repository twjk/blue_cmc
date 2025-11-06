package com.qcmz.cmc.entity;

/**
 * CmcCtSubjectcat entity. @author MyEclipse Persistence Tools
 */

public class CmcCtSubjectcat implements java.io.Serializable {

	// Fields

	private Long catid;
	private String catname;
	private String desctemplet;

	// Constructors

	/** default constructor */
	public CmcCtSubjectcat() {
	}

	// Property accessors

	public Long getCatid() {
		return this.catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return this.catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getDesctemplet() {
		return this.desctemplet;
	}

	public void setDesctemplet(String desctemplet) {
		this.desctemplet = desctemplet;
	}

}