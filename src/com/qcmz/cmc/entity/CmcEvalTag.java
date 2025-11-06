package com.qcmz.cmc.entity;

/**
 * CmcEvalTag entity. @author MyEclipse Persistence Tools
 */

public class CmcEvalTag implements java.io.Serializable {

	// Fields

	private Long tagid;
	private Long evalid;
	private CmcEval cmcEval;
	private String tag;

	// Constructors

	/** default constructor */
	public CmcEvalTag() {
	}

	// Property accessors

	public Long getTagid() {
		return this.tagid;
	}

	public void setTagid(Long tagid) {
		this.tagid = tagid;
	}

	public Long getEvalid() {
		return evalid;
	}

	public void setEvalid(Long evalid) {
		this.evalid = evalid;
	}

	public CmcEval getCmcEval() {
		return this.cmcEval;
	}

	public void setCmcEval(CmcEval cmcEval) {
		this.cmcEval = cmcEval;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}