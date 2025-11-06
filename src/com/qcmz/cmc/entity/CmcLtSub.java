package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcLtSub entity. @author MyEclipse Persistence Tools
 */

public class CmcLtSub implements java.io.Serializable {

	// Fields

	private Long subid;
	private Long userid;
	private UserSimpleBean user;
	private Integer workyear;
	private Integer edu;
	private Long cityid;
	private String cityname;
	private Integer worktimetype;
	private String title;
	private Integer reward;
	private Integer rewardtype;
	private String address;
	private String lon;
	private String lat;
	private String content;
	private Date createtime;
	private Date updatetime;
	private Date notifytime;

	
	// Constructors

	/** default constructor */
	public CmcLtSub() {
	}

	// Property accessors

	public Long getSubid() {
		return this.subid;
	}

	public void setSubid(Long subid) {
		this.subid = subid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public Integer getWorkyear() {
		return this.workyear;
	}

	public void setWorkyear(Integer workyear) {
		this.workyear = workyear;
	}

	public Integer getEdu() {
		return this.edu;
	}

	public void setEdu(Integer edu) {
		this.edu = edu;
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

	public Integer getWorktimetype() {
		return this.worktimetype;
	}

	public void setWorktimetype(Integer worktimetype) {
		this.worktimetype = worktimetype;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getReward() {
		return this.reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public Integer getRewardtype() {
		return rewardtype;
	}

	public void setRewardtype(Integer rewardtype) {
		this.rewardtype = rewardtype;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLon() {
		return this.lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getNotifytime() {
		return notifytime;
	}

	public void setNotifytime(Date notifytime) {
		this.notifytime = notifytime;
	}
}