package com.qcmz.cmc.entity;

/**
 * CmcCtPlatform entity. @author MyEclipse Persistence Tools
 */

public class CmcCtPlatform implements java.io.Serializable {

	// Fields

	private Long id;
	private Long taskid;
	private CmcCtTask cmcCtTask;
	private String servicetype;
	private String platform;
	private String minversion;
	private String maxversion;

	// Constructors

	/** default constructor */
	public CmcCtPlatform() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public CmcCtTask getCmcCtTask() {
		return this.cmcCtTask;
	}

	public void setCmcCtTask(CmcCtTask cmcCtTask) {
		this.cmcCtTask = cmcCtTask;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getPlatform() {
		return platform;
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