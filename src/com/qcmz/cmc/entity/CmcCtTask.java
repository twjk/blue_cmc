package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CmcCtTask entity. @author MyEclipse Persistence Tools
 */

public class CmcCtTask implements java.io.Serializable {

	// Fields

	private Long taskid;
	private Integer tasksource;
	private Integer tasktype;
	private Long pretaskid;
	private Integer taskfreq;
	private Long parenttaskid;
	private String groupcode;
	private Long libid;
	private CmcCtLib cmcCtLib;
	private String title;
	private String titlebase;
	private String remark;
	private Date starttime;
	private Date endtime;
	private Integer autocancel;
	private String pic;
	private String pic2;
	private String pic3;
	private Integer subjectassign;
	private Integer subjectassignseq;
	private Integer subjectnum;
	private Double taskreward;
	private Integer taskrewardsettle;
	private Integer autoverify;
	private Double verifyreward;
	private Integer verifypassscore;
	private Integer autoqc;
	private Double qcreward;
	private Integer qcrewardmeasure;
	private Integer rewardtype;
	private String unitname;
	private Integer maxnum;
	private Integer applynum;
	private Integer ingnum;
	private Integer finishnum;
	private String description;
	private Date createtime;
	private Integer sortindex;
	private String limituserid;
	private String orderid;
	private Long actid;
	private CmcRewardActivity activity;
	private Integer status;
	
	private List<CmcCtSubject> subjects = new ArrayList<CmcCtSubject>(0);
	private List<CmcCtOption> options = new ArrayList<CmcCtOption>(0);
	private List<CmcCtPlatform> platforms = new ArrayList<CmcCtPlatform>(0);
	private List<Long> limitUserIds = new ArrayList<Long>();

	// Constructors
	/** default constructor */
	public CmcCtTask() {
	}

	// Property accessors

	public Long getTaskid() {
		return this.taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public Integer getTasksource() {
		return this.tasksource;
	}

	public void setTasksource(Integer tasksource) {
		this.tasksource = tasksource;
	}

	public Integer getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
	}

	public Long getPretaskid() {
		return this.pretaskid;
	}

	public void setPretaskid(Long pretaskid) {
		this.pretaskid = pretaskid;
	}

	public Integer getTaskfreq() {
		return this.taskfreq;
	}

	public void setTaskfreq(Integer taskfreq) {
		this.taskfreq = taskfreq;
	}

	public Long getParenttaskid() {
		return this.parenttaskid;
	}

	public void setParenttaskid(Long parenttaskid) {
		this.parenttaskid = parenttaskid;
	}

	public String getGroupcode() {
		return groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	public Long getLibid() {
		return this.libid;
	}

	public void setLibid(Long libid) {
		this.libid = libid;
	}

	public CmcCtLib getCmcCtLib() {
		return cmcCtLib;
	}

	public void setCmcCtLib(CmcCtLib cmcCtLib) {
		this.cmcCtLib = cmcCtLib;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitlebase() {
		return titlebase;
	}

	public void setTitlebase(String titlebase) {
		this.titlebase = titlebase;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getAutocancel() {
		return autocancel;
	}

	public void setAutocancel(Integer autocancel) {
		this.autocancel = autocancel;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPic2() {
		return this.pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return this.pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public Integer getSubjectassign() {
		return this.subjectassign;
	}

	public void setSubjectassign(Integer subjectassign) {
		this.subjectassign = subjectassign;
	}

	public Integer getSubjectassignseq() {
		return subjectassignseq;
	}

	public void setSubjectassignseq(Integer subjectassignseq) {
		this.subjectassignseq = subjectassignseq;
	}

	public Integer getSubjectnum() {
		return this.subjectnum;
	}

	public void setSubjectnum(Integer subjectnum) {
		this.subjectnum = subjectnum;
	}

	public Double getTaskreward() {
		return this.taskreward;
	}

	public void setTaskreward(Double taskreward) {
		this.taskreward = taskreward;
	}

	public Integer getTaskrewardsettle() {
		return this.taskrewardsettle;
	}

	public void setTaskrewardsettle(Integer taskrewardsettle) {
		this.taskrewardsettle = taskrewardsettle;
	}

	public Integer getAutoverify() {
		return this.autoverify;
	}

	public void setAutoverify(Integer autoverify) {
		this.autoverify = autoverify;
	}

	public Double getVerifyreward() {
		return this.verifyreward;
	}

	public void setVerifyreward(Double verifyreward) {
		this.verifyreward = verifyreward;
	}

	public Integer getVerifypassscore() {
		return this.verifypassscore;
	}

	public void setVerifypassscore(Integer verifypassscore) {
		this.verifypassscore = verifypassscore;
	}

	public Integer getAutoqc() {
		return this.autoqc;
	}

	public void setAutoqc(Integer autoqc) {
		this.autoqc = autoqc;
	}

	public Double getQcreward() {
		return this.qcreward;
	}

	public void setQcreward(Double qcreward) {
		this.qcreward = qcreward;
	}

	public Integer getQcrewardmeasure() {
		return qcrewardmeasure;
	}

	public void setQcrewardmeasure(Integer qcrewardmeasure) {
		this.qcrewardmeasure = qcrewardmeasure;
	}

	public Integer getRewardtype() {
		return this.rewardtype;
	}

	public void setRewardtype(Integer rewardtype) {
		this.rewardtype = rewardtype;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public Integer getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public Integer getApplynum() {
		return this.applynum;
	}

	public void setApplynum(Integer applynum) {
		this.applynum = applynum;
	}

	public Integer getIngnum() {
		return this.ingnum;
	}

	public void setIngnum(Integer ingnum) {
		this.ingnum = ingnum;
	}

	public Integer getFinishnum() {
		return this.finishnum;
	}

	public void setFinishnum(Integer finishnum) {
		this.finishnum = finishnum;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getLimituserid() {
		return this.limituserid;
	}

	public void setLimituserid(String limituserid) {
		this.limituserid = limituserid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CmcCtSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<CmcCtSubject> subjects) {
		this.subjects = subjects;
	}

	public List<CmcCtOption> getOptions() {
		return options;
	}

	public void setOptions(List<CmcCtOption> options) {
		this.options = options;
	}

	public List<CmcCtPlatform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<CmcCtPlatform> platforms) {
		this.platforms = platforms;
	}

	public List<Long> getLimitUserIds() {
		return limitUserIds;
	}

	public void setLimitUserIds(List<Long> limitUserIds) {
		this.limitUserIds = limitUserIds;
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
}