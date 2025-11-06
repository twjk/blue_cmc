package com.qcmz.cmc.entity;

/**
 * CmcFeeRuledetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcFeeRuledetail implements java.io.Serializable {

	// Fields

	private Long detailid;
	private CmcFeeRule cmcFeeRule;
	private String item;
	private String langcode;
	private Integer point;
	private String description;

	// Constructors

	/** default constructor */
	public CmcFeeRuledetail() {
	}

	/** minimal constructor */
	public CmcFeeRuledetail(CmcFeeRule cmcFeeRule, String item, Integer point) {
		this.cmcFeeRule = cmcFeeRule;
		this.item = item;
		this.point = point;
	}

	/** full constructor */
	public CmcFeeRuledetail(CmcFeeRule cmcFeeRule, String item,
			String langcode, Integer point, String description) {
		this.cmcFeeRule = cmcFeeRule;
		this.item = item;
		this.langcode = langcode;
		this.point = point;
		this.description = description;
	}

	// Property accessors

	public Long getDetailid() {
		return this.detailid;
	}

	public void setDetailid(Long detailid) {
		this.detailid = detailid;
	}

	public CmcFeeRule getCmcFeeRule() {
		return this.cmcFeeRule;
	}

	public void setCmcFeeRule(CmcFeeRule cmcFeeRule) {
		this.cmcFeeRule = cmcFeeRule;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}