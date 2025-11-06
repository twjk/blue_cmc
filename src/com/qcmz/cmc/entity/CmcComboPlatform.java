package com.qcmz.cmc.entity;

/**
 * CmcComboPlatform entity. @author MyEclipse Persistence Tools
 */

public class CmcComboPlatform implements java.io.Serializable {

	// Fields

	private Long id;
	private Long comboid;
	private CmcCombo cmcCombo;
	private String platform;
	private String minversion;
	private String maxversion;

	// Constructors

	/** default constructor */
	public CmcComboPlatform() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getComboid() {
		return comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public CmcCombo getCmcCombo() {
		return this.cmcCombo;
	}

	public void setCmcCombo(CmcCombo cmcCombo) {
		this.cmcCombo = cmcCombo;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getMinversion() {
		return this.minversion;
	}

	public void setMinversion(String minversion) {
		this.minversion = minversion;
	}

	public String getMaxversion() {
		return this.maxversion;
	}

	public void setMaxversion(String maxversion) {
		this.maxversion = maxversion;
	}

}