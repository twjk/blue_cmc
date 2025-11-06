package com.qcmz.cmc.entity;

import com.qcmz.framework.constant.SystemConstants;

/**
 * CmcJobArticlePic entity. @author MyEclipse Persistence Tools
 */

public class CmcJobArticlePic implements java.io.Serializable {

	// Fields

	private Long picid;
	private Long artid;
	private CmcJobArticle cmcJobArticle;
	private String picurl;
	private String thumburl;
	private Integer sortindex = SystemConstants.SORTINDEX_DEFAULT;

	// Constructors

	/** default constructor */
	public CmcJobArticlePic() {
	}
	
	public CmcJobArticlePic(String picurl) {
		super();
		this.picurl = picurl;
	}

	// Property accessors

	public Long getPicid() {
		return this.picid;
	}

	public void setPicid(Long picid) {
		this.picid = picid;
	}

	public Long getArtid() {
		return artid;
	}

	public void setArtid(Long artid) {
		this.artid = artid;
	}

	public CmcJobArticle getCmcJobArticle() {
		return this.cmcJobArticle;
	}

	public void setCmcJobArticle(CmcJobArticle cmcJobArticle) {
		this.cmcJobArticle = cmcJobArticle;
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