package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * CmcEval entity. @author MyEclipse Persistence Tools
 */

public class CmcEval implements java.io.Serializable {

	// Fields

	private Long evalid;
	private Integer evaltype;
	private Integer evallevel;
	private String levelname;
	private String icon;
	private String icon2;
	private Integer status;
	private List<CmcEvalTag> cmcEvalTags = new ArrayList<CmcEvalTag>(0);

	// Constructors

	/** default constructor */
	public CmcEval() {
	}

	// Property accessors

	public Long getEvalid() {
		return this.evalid;
	}

	public void setEvalid(Long evalid) {
		this.evalid = evalid;
	}

	public Integer getEvaltype() {
		return this.evaltype;
	}

	public void setEvaltype(Integer evaltype) {
		this.evaltype = evaltype;
	}

	public Integer getEvallevel() {
		return this.evallevel;
	}

	public void setEvallevel(Integer evallevel) {
		this.evallevel = evallevel;
	}

	public String getLevelname() {
		return this.levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon2() {
		return icon2;
	}

	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CmcEvalTag> getCmcEvalTags() {
		return cmcEvalTags;
	}

	public void setCmcEvalTags(List<CmcEvalTag> cmcEvalTags) {
		this.cmcEvalTags = cmcEvalTags;
	}
}