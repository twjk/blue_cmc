package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcLtSpd entity. @author MyEclipse Persistence Tools
 */

public class CmcLtSpd implements java.io.Serializable {

	// Fields

	private Long spdid;
	private Long spdtaskid;
	private CmcLtSpdtask cmcLtSpdtask;
	private Long sourceid;
	private CmcLtSource cmcLtSource;
	private String title;
	private String pic;
	private String link;
	private String outidentify;
	private Date publishtime;
	private Date spdtime;
	private String dealprogress;
	private Date operstarttime;
	private Date operendtime;
	private String opercd;
	private String opername;

	// Constructors

	/** default constructor */
	public CmcLtSpd() {
	}

	// Property accessors

	public Long getSpdid() {
		return this.spdid;
	}

	public void setSpdid(Long spdid) {
		this.spdid = spdid;
	}

	public Long getSpdtaskid() {
		return spdtaskid;
	}

	public void setSpdtaskid(Long spdtaskid) {
		this.spdtaskid = spdtaskid;
	}

	public CmcLtSpdtask getCmcLtSpdtask() {
		return this.cmcLtSpdtask;
	}

	public void setCmcLtSpdtask(CmcLtSpdtask cmcLtSpdtask) {
		this.cmcLtSpdtask = cmcLtSpdtask;
	}

	public Long getSourceid() {
		return sourceid;
	}

	public void setSourceid(Long sourceid) {
		this.sourceid = sourceid;
	}

	public CmcLtSource getCmcLtSource() {
		return cmcLtSource;
	}

	public void setCmcLtSource(CmcLtSource cmcLtSource) {
		this.cmcLtSource = cmcLtSource;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getOutidentify() {
		return this.outidentify;
	}

	public void setOutidentify(String outidentify) {
		this.outidentify = outidentify;
	}

	public Date getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Date getSpdtime() {
		return this.spdtime;
	}

	public void setSpdtime(Date spdtime) {
		this.spdtime = spdtime;
	}

	public String getDealprogress() {
		return this.dealprogress;
	}

	public void setDealprogress(String dealprogress) {
		this.dealprogress = dealprogress;
	}

	public Date getOperstarttime() {
		return this.operstarttime;
	}

	public void setOperstarttime(Date operstarttime) {
		this.operstarttime = operstarttime;
	}

	public Date getOperendtime() {
		return this.operendtime;
	}

	public void setOperendtime(Date operendtime) {
		this.operendtime = operendtime;
	}

	public String getOpercd() {
		return this.opercd;
	}

	public void setOpercd(String opercd) {
		this.opercd = opercd;
	}

	public String getOpername() {
		return this.opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

}