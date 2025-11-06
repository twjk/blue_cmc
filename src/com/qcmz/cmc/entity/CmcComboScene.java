package com.qcmz.cmc.entity;

/**
 * CmcComboScene entity. @author MyEclipse Persistence Tools
 */

public class CmcComboScene implements java.io.Serializable {

	// Fields

	private Long id;
	private Long comboid;
	private CmcCombo cmcCombo;
	private Long sceneid;
	private CmcBScene cmcBScene;

	// Constructors

	/** default constructor */
	public CmcComboScene() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSceneid() {
		return sceneid;
	}

	public void setSceneid(Long sceneid) {
		this.sceneid = sceneid;
	}

	public CmcBScene getCmcBScene() {
		return cmcBScene;
	}

	public void setCmcBScene(CmcBScene cmcBScene) {
		this.cmcBScene = cmcBScene;
	}

	public Long getComboid() {
		return comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public CmcCombo getCmcCombo() {
		return cmcCombo;
	}

	public void setCmcCombo(CmcCombo cmcCombo) {
		this.cmcCombo = cmcCombo;
	}
}