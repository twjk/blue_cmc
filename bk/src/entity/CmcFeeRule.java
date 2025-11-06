package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * CmcFeeRule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcFeeRule implements java.io.Serializable {

	// Fields

	private Long ruleid;
	private String description;
	private Integer status;
	private List<CmcFeeRuledetail> cmcFeeRuledetails = new ArrayList<CmcFeeRuledetail>();

	// Constructors

	/** default constructor */
	public CmcFeeRule() {
	}
	// Property accessors

	public Long getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CmcFeeRuledetail> getCmcFeeRuledetails() {
		return cmcFeeRuledetails;
	}

	public void setCmcFeeRuledetails(List<CmcFeeRuledetail> cmcFeeRuledetails) {
		this.cmcFeeRuledetails = cmcFeeRuledetails;
	}
}