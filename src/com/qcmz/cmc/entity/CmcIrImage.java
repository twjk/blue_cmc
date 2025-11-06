package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcIrImage entity. @author MyEclipse Persistence Tools
 */

public class CmcIrImage implements java.io.Serializable {

	// Fields

	private Long imageid;
	private Long catid;
	private CmcIrCat cmcIrCat;
	private Long userid;
	private String imageurl;
	private String thumburl;
	private Date createtime;
	
	private List<CmcIrRecognition> cmcIrRecognitions = new ArrayList<CmcIrRecognition>(0);
	private UserSimpleBean user;
	
	// Constructors

	/** default constructor */
	public CmcIrImage() {
	}

	// Property accessors

	public Long getImageid() {
		return this.imageid;
	}

	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}

	public Long getCatid() {
		return catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public CmcIrCat getCmcIrCat() {
		return this.cmcIrCat;
	}

	public void setCmcIrCat(CmcIrCat cmcIrCat) {
		this.cmcIrCat = cmcIrCat;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getImageurl() {
		return this.imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getThumburl() {
		return thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<CmcIrRecognition> getCmcIrRecognitions() {
		return cmcIrRecognitions;
	}

	public void setCmcIrRecognitions(List<CmcIrRecognition> cmcIrRecognitions) {
		this.cmcIrRecognitions = cmcIrRecognitions;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}