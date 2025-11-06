package com.qcmz.cmc.entity;

/**
 * CmcLtSource entity. @author MyEclipse Persistence Tools
 */

public class CmcLtSource implements java.io.Serializable {

	// Fields

	private Long sourceid;
	private String sourcename;
	private Integer sourcetype;
	private Long cityid;
	private String cityname;
	private Long spdcount;
	private Long taskcount;
	private Long articlecount;
	
	// Constructors

	/** default constructor */
	public CmcLtSource() {
	}

	// Property accessors

	public Long getSourceid() {
		return this.sourceid;
	}

	public void setSourceid(Long sourceid) {
		this.sourceid = sourceid;
	}

	public String getSourcename() {
		return this.sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public Integer getSourcetype() {
		return this.sourcetype;
	}

	public void setSourcetype(Integer sourcetype) {
		this.sourcetype = sourcetype;
	}

	public Long getCityid() {
		return this.cityid;
	}

	public void setCityid(Long cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Long getSpdcount() {
		return spdcount;
	}

	public void setSpdcount(Long spdcount) {
		this.spdcount = spdcount;
	}

	public Long getTaskcount() {
		return taskcount;
	}

	public void setTaskcount(Long taskcount) {
		this.taskcount = taskcount;
	}

	public Long getArticlecount() {
		return articlecount;
	}

	public void setArticlecount(Long articlecount) {
		this.articlecount = articlecount;
	}
}