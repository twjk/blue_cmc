package com.qcmz.cmc.entity;

/**
 * CmcBScenecat entity. @author MyEclipse Persistence Tools
 */

public class CmcBScenecat implements java.io.Serializable {

	// Fields

	private Long catid;
	private String catname;
	private Integer sortindex;

	// Constructors

	/** default constructor */
	public CmcBScenecat() {
	}

	/** full constructor */
	public CmcBScenecat(String catname, Integer sortindex) {
		this.catname = catname;
		this.sortindex = sortindex;
	}

	// Property accessors

	public Long getCatid() {
		return this.catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return this.catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

}