package com.qcmz.cmc.entity;

/**
 * CmcLtPic entity. @author MyEclipse Persistence Tools
 */

public class CmcLtPic implements java.io.Serializable {

	// Fields

	private Long picid;
	private Long taskid;
	private CmcLtTask cmcLtTask;
	private String picurl;
	private String thumburl;
	private Integer sortindex;

	// Constructors

	/** default constructor */
	public CmcLtPic() {
	}

	// Property accessors

	public Long getPicid() {
		return this.picid;
	}

	public void setPicid(Long picid) {
		this.picid = picid;
	}

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public CmcLtTask getCmcLtTask() {
		return this.cmcLtTask;
	}

	public void setCmcLtTask(CmcLtTask cmcLtTask) {
		this.cmcLtTask = cmcLtTask;
	}

	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getThumburl() {
		return this.thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

}