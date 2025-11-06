package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.framework.constant.SystemConstants;

/**
 * CmcJobArticle entity. @author MyEclipse Persistence Tools
 */

public class CmcJobArticle implements java.io.Serializable {

	// Fields

	private Long artid;
	private String fullcatid;
	private Long catid;
	private Long sourceid;
	private CmcLtSource cmcLtSource;
	private String title;
	private Long cityid;
	private String cityname;
	private String link;
	private Date publishtime;
	private Integer sortindex = SystemConstants.SORTINDEX_DEFAULT;
	private String sortquery;
	private Long actid;
	private CmcRewardActivity activity;
	private Long spdid;
	private Integer status = SystemConstants.STATUS_ON;
	
	private List<CmcJobArticlePic> pics = new ArrayList<CmcJobArticlePic>(0);

	// Constructors

	/** default constructor */
	public CmcJobArticle() {
	}

	// Property accessors

	public String getFullcatid() {
		return this.fullcatid;
	}

	public Long getArtid() {
		return artid;
	}

	public void setArtid(Long artid) {
		this.artid = artid;
	}

	public void setFullcatid(String fullcatid) {
		this.fullcatid = fullcatid;
	}

	public Long getCatid() {
		return catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public Long getSourceid() {
		return this.sourceid;
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

	public Long getCityid() {
		return this.cityid;
	}

	public void setCityid(Long cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

	public String getSortquery() {
		return sortquery;
	}

	public void setSortquery(String sortquery) {
		this.sortquery = sortquery;
	}

	public Long getActid() {
		return actid;
	}

	public void setActid(Long actid) {
		this.actid = actid;
	}

	public CmcRewardActivity getActivity() {
		return activity;
	}

	public void setActivity(CmcRewardActivity activity) {
		this.activity = activity;
	}

	public Long getSpdid() {
		return spdid;
	}

	public void setSpdid(Long spdid) {
		this.spdid = spdid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CmcJobArticlePic> getPics() {
		return pics;
	}

	public void setPics(List<CmcJobArticlePic> pics) {
		this.pics = pics;
	}
}