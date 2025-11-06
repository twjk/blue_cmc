package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CmcLtTask entity. @author MyEclipse Persistence Tools
 */

public class CmcLtTask implements java.io.Serializable {

	// Fields
	private Long taskid;
	private Long userid = 0L;
	private Long companyid;
	private CmcLtCompany cmcLtCompany;
	private Long sourceid;
	private CmcLtSource cmcLtSource;
	private String title;
	private Integer worktimetype;
	private String reward;
	private Integer minreward;
	private Integer maxreward;
	private Integer refreward;
	private Integer rewardtype;
	private String contact;
	private Long cityid = 0L;
	private String cityname;
	private String address;
	private String lon;
	private String lat;
	private String exp;
	private Integer minexp = 0;
	private String edu;
	private Integer minedu = 0;
	private String peoplenum;
	private String job;
	private String jobrequire;
	private Date publishtime;
	private Date createtime;
	
	private Integer sortindex = 999;
	private String sortquery;
	private Long actid;
	private CmcRewardActivity activity;
	private Integer status = 1;
	private String verifyresult;
	private Integer createway = 1;
	private String link;
	private Long spdid;
	
	private List<CmcLtPic> cmcLtPics = new ArrayList<CmcLtPic>(0);

	// Constructors

	/** default constructor */
	public CmcLtTask() {
	}

	// Property accessors

	public Long getTaskid() {
		return this.taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWorktimetype() {
		return worktimetype;
	}

	public void setWorktimetype(Integer worktimetype) {
		this.worktimetype = worktimetype;
	}

	public String getReward() {
		return this.reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public Integer getRewardtype() {
		return rewardtype;
	}

	public void setRewardtype(Integer rewardtype) {
		this.rewardtype = rewardtype;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getExp() {
		return this.exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getEdu() {
		return this.edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getPeoplenum() {
		return this.peoplenum;
	}

	public void setPeoplenum(String peoplenum) {
		this.peoplenum = peoplenum;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobrequire() {
		return this.jobrequire;
	}

	public void setJobrequire(String jobrequire) {
		this.jobrequire = jobrequire;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVerifyresult() {
		return verifyresult;
	}

	public void setVerifyresult(String verifyresult) {
		this.verifyresult = verifyresult;
	}

	public Long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}

	public CmcLtCompany getCmcLtCompany() {
		return cmcLtCompany;
	}

	public void setCmcLtCompany(CmcLtCompany cmcLtCompany) {
		this.cmcLtCompany = cmcLtCompany;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getCreateway() {
		return createway;
	}

	public void setCreateway(Integer createway) {
		this.createway = createway;
	}

	public Long getSpdid() {
		return spdid;
	}

	public Integer getMinreward() {
		return minreward;
	}

	public void setMinreward(Integer minreward) {
		this.minreward = minreward;
	}

	public Integer getMaxreward() {
		return maxreward;
	}

	public void setMaxreward(Integer maxreward) {
		this.maxreward = maxreward;
	}

	public Integer getRefreward() {
		return refreward;
	}

	public void setRefreward(Integer refreward) {
		this.refreward = refreward;
	}

	public Integer getMinexp() {
		return minexp;
	}

	public void setMinexp(Integer minexp) {
		this.minexp = minexp;
	}

	public Integer getMinedu() {
		return minedu;
	}

	public void setMinedu(Integer minedu) {
		this.minedu = minedu;
	}

	public void setSpdid(Long spdid) {
		this.spdid = spdid;
	}

	public List<CmcLtPic> getCmcLtPics() {
		return cmcLtPics;
	}

	public void setCmcLtPics(List<CmcLtPic> cmcLtPics) {
		this.cmcLtPics = cmcLtPics;
	}
}