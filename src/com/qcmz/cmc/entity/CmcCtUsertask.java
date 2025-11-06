package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcCtUsertask entity. @author MyEclipse Persistence Tools
 */

public class CmcCtUsertask implements java.io.Serializable {

	// Fields
	private String utid;
	private Long taskid;
	private CmcCtTask cmcCtTask;
	private String servicetype;
	private Long userid;
	private UserSimpleBean user;
	private Long pretaskid;
	private Date applytime;
	private Date starttime;
	private Date finishtime;
	private Integer subjectnum;
	private Integer finishsubjectnum = 0;
	private Double taskreward;
	private Double utreward;
	private Integer utrewardstatus;
	private Date utrewardtime;
	private String lon;
	private String lat;
	private String countryname;
	private String cityname;
	private String platform;
	private String version;
	private String uuid;
	private String refusereason;
	private Integer verifystatus;
	private Integer verifypass;
	private Long verifyuserid;
	private UserSimpleBean verifyUser;
	private Date verifystarttime;
	private Date verifyfinishtime;
	private Double verifyreward;
	private Integer verifyrewardstatus;
	private Date verifyrewardtime;
	private String cancelreason;
	private Date canceltime;
	private String cancelcd;
	private String cancelname;
	private Integer status;
	
	/**
	 * 是否黑名单用户
	 */
	private boolean inBlacklist;
	private List<CmcCtUsersubject> userSubjects = new ArrayList<CmcCtUsersubject>();
	
	// Constructors

	/** default constructor */
	public CmcCtUsertask() {
	}
	
	// Property accessors

	public String getUtid() {
		return this.utid;
	}

	public void setUtid(String utid) {
		this.utid = utid;
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

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getPretaskid() {
		return this.pretaskid;
	}

	public void setPretaskid(Long pretaskid) {
		this.pretaskid = pretaskid;
	}

	public Date getApplytime() {
		return this.applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public Integer getSubjectnum() {
		return subjectnum;
	}

	public void setSubjectnum(Integer subjectnum) {
		this.subjectnum = subjectnum;
	}

	public Integer getFinishsubjectnum() {
		return finishsubjectnum;
	}

	public void setFinishsubjectnum(Integer finishsubjectnum) {
		this.finishsubjectnum = finishsubjectnum;
	}

	public Double getTaskreward() {
		return taskreward;
	}

	public void setTaskreward(Double taskreward) {
		this.taskreward = taskreward;
	}

	public Double getUtreward() {
		return this.utreward;
	}

	public void setUtreward(Double utreward) {
		this.utreward = utreward;
	}

	public Date getUtrewardtime() {
		return this.utrewardtime;
	}

	public void setUtrewardtime(Date utrewardtime) {
		this.utrewardtime = utrewardtime;
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

	public String getCountryname() {
		return this.countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}

	public Date getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}

	public String getRefusereason() {
		return this.refusereason;
	}

	public void setRefusereason(String refusereason) {
		this.refusereason = refusereason;
	}

	public Integer getVerifystatus() {
		return this.verifystatus;
	}

	public void setVerifystatus(Integer verifystatus) {
		this.verifystatus = verifystatus;
	}

	public Integer getVerifypass() {
		return verifypass;
	}

	public void setVerifypass(Integer verifypass) {
		this.verifypass = verifypass;
	}

	public Long getVerifyuserid() {
		return this.verifyuserid;
	}

	public void setVerifyuserid(Long verifyuserid) {
		this.verifyuserid = verifyuserid;
	}

	public Date getVerifystarttime() {
		return this.verifystarttime;
	}

	public void setVerifystarttime(Date verifystarttime) {
		this.verifystarttime = verifystarttime;
	}
	
	public Date getVerifyfinishtime() {
		return verifyfinishtime;
	}

	public void setVerifyfinishtime(Date verifyfinishtime) {
		this.verifyfinishtime = verifyfinishtime;
	}

	public Double getVerifyreward() {
		return this.verifyreward;
	}

	public void setVerifyreward(Double verifyreward) {
		this.verifyreward = verifyreward;
	}

	public Date getVerifyrewardtime() {
		return this.verifyrewardtime;
	}

	public void setVerifyrewardtime(Date verifyrewardtime) {
		this.verifyrewardtime = verifyrewardtime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUtrewardstatus() {
		return utrewardstatus;
	}

	public void setUtrewardstatus(Integer utrewardstatus) {
		this.utrewardstatus = utrewardstatus;
	}

	public Integer getVerifyrewardstatus() {
		return verifyrewardstatus;
	}

	public void setVerifyrewardstatus(Integer verifyrewardstatus) {
		this.verifyrewardstatus = verifyrewardstatus;
	}
	
	public String getCancelcd() {
		return cancelcd;
	}

	public void setCancelcd(String cancelcd) {
		this.cancelcd = cancelcd;
	}

	public String getCancelname() {
		return cancelname;
	}

	public void setCancelname(String cancelname) {
		this.cancelname = cancelname;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public UserSimpleBean getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(UserSimpleBean verifyUser) {
		this.verifyUser = verifyUser;
	}

	public boolean isInBlacklist() {
		return inBlacklist;
	}

	public void setInBlacklist(boolean inBlacklist) {
		this.inBlacklist = inBlacklist;
	}

	public List<CmcCtUsersubject> getUserSubjects() {
		return userSubjects;
	}

	public void setUserSubjects(List<CmcCtUsersubject> userSubjects) {
		this.userSubjects = userSubjects;
	}
	
}