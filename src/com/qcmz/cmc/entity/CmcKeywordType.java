package com.qcmz.cmc.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * CmcKeywordType entity. @author MyEclipse Persistence Tools
 */

public class CmcKeywordType implements java.io.Serializable {

	// Fields

	private Long typeid;
	private String typename;
	private String icon;
	private String typeurl;
	private Integer status;
	

	// Constructors

	/** default constructor */
	public CmcKeywordType() {
	}

	// Property accessors

	public Long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTypeurl() {
		return typeurl;
	}

	public void setTypeurl(String typeurl) {
		this.typeurl = typeurl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}