package com.qcmz.cmc.entity;

/**
 * CmcBScene entity. @author MyEclipse Persistence Tools
 */

public class CmcBScene implements java.io.Serializable {

	// Fields

	private Long sceneid;
	private String transtype;
	private Long catid;
	private CmcBScenecat cmcBScenecat;
	private String scenename;
	private String icon;
	private Integer sortindex;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcBScene() {
	}

	// Property accessors

	public Long getSceneid() {
		return this.sceneid;
	}

	public void setSceneid(Long sceneid) {
		this.sceneid = sceneid;
	}

	public String getTranstype() {
		return this.transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public Long getCatid() {
		return catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public CmcBScenecat getCmcBScenecat() {
		return cmcBScenecat;
	}

	public void setCmcBScenecat(CmcBScenecat cmcBScenecat) {
		this.cmcBScenecat = cmcBScenecat;
	}

	public String getScenename() {
		return this.scenename;
	}

	public void setScenename(String scenename) {
		this.scenename = scenename;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}