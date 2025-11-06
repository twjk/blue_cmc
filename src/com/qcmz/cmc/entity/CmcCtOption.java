package com.qcmz.cmc.entity;

/**
 * CmcCtOption entity. @author MyEclipse Persistence Tools
 */

public class CmcCtOption implements java.io.Serializable {

	// Fields
	private Long optionid;
	private Long libid;
	private CmcCtLib cmcCtLib;
	private Long subjectid;
	private CmcCtSubject cmcCtSubject;
	private String optionvalue;
	private String optiontitle;
	private Integer writemore;

	// Constructors

	/** default constructor */
	public CmcCtOption() {
	}

	// Property accessors

	public Long getOptionid() {
		return this.optionid;
	}

	public void setOptionid(Long optionid) {
		this.optionid = optionid;
	}

	public Long getLibid() {
		return libid;
	}

	public void setLibid(Long libid) {
		this.libid = libid;
	}

	public CmcCtLib getCmcCtLib() {
		return cmcCtLib;
	}

	public void setCmcCtLib(CmcCtLib cmcCtLib) {
		this.cmcCtLib = cmcCtLib;
	}

	public Long getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Long subjectid) {
		this.subjectid = subjectid;
	}

	public CmcCtSubject getCmcCtSubject() {
		return this.cmcCtSubject;
	}

	public void setCmcCtSubject(CmcCtSubject cmcCtSubject) {
		this.cmcCtSubject = cmcCtSubject;
	}

	public String getOptionvalue() {
		return optionvalue;
	}

	public void setOptionvalue(String optionvalue) {
		this.optionvalue = optionvalue;
	}

	public String getOptiontitle() {
		return this.optiontitle;
	}

	public void setOptiontitle(String optiontitle) {
		this.optiontitle = optiontitle;
	}

	public Integer getWritemore() {
		return this.writemore;
	}

	public void setWritemore(Integer writemore) {
		this.writemore = writemore;
	}

}