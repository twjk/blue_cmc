package com.qcmz.cmc.entity;

/**
 * CmcComboLang entity. @author MyEclipse Persistence Tools
 */

public class CmcComboLang implements java.io.Serializable {

	// Fields

	private Long id;
	private Long comboid;
	private CmcCombo cmcCombo;
	private String fromlang;
	private String tolang;

	// Constructors

	/** default constructor */
	public CmcComboLang() {
	}

	/** minimal constructor */
	public CmcComboLang(CmcCombo cmcCombo) {
		this.cmcCombo = cmcCombo;
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

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

}