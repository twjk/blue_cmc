package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CmcCtUsersubject entity. @author MyEclipse Persistence Tools
 */

public class CmcCtUsersubject implements java.io.Serializable {

	// Fields

	private Long usid;
	private String utid;
	private CmcCtUsertask cmcCtUsertask;
	private Integer tasktype;
	private Long taskid;
	private CmcCtTask cmcCtTask;
	private Long subjectid;
	private CmcCtSubject cmcCtSubject;
	private Integer answerstatus;
	private Date answertime;
	private Double subjectreward;
	private Double usreward;
	private Integer usrewardstatus;
	private Date usrewardtime;
	private Integer qcstatus;
	private Long qcid;
	private CmcCtQc cmcCtQc;
	private Integer score;
	private Integer compositescore;

	private List<CmcCtUseranswer> userAnswers = new ArrayList<CmcCtUseranswer>();
	
	// Constructors

	/** default constructor */
	public CmcCtUsersubject() {
	}

	// Property accessors

	public Long getUsid() {
		return this.usid;
	}

	public void setUsid(Long usid) {
		this.usid = usid;
	}

	public String getUtid() {
		return utid;
	}

	public void setUtid(String utid) {
		this.utid = utid;
	}

	public CmcCtUsertask getCmcCtUsertask() {
		return cmcCtUsertask;
	}

	public void setCmcCtUsertask(CmcCtUsertask cmcCtUsertask) {
		this.cmcCtUsertask = cmcCtUsertask;
	}

	public Integer getTasktype() {
		return tasktype;
	}

	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
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

	public Long getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Long subjectid) {
		this.subjectid = subjectid;
	}

	public CmcCtSubject getCmcCtSubject() {
		return this.cmcCtSubject;
	}

	public void setCmcCtSubject(CmcCtSubject cmcCtSubject) {
		this.cmcCtSubject = cmcCtSubject;
	}

	public Integer getAnswerstatus() {
		return answerstatus;
	}

	public void setAnswerstatus(Integer answerstatus) {
		this.answerstatus = answerstatus;
	}

	public Date getAnswertime() {
		return answertime;
	}

	public void setAnswertime(Date answertime) {
		this.answertime = answertime;
	}

	public Integer getUsrewardstatus() {
		return usrewardstatus;
	}

	public void setUsrewardstatus(Integer usrewardstatus) {
		this.usrewardstatus = usrewardstatus;
	}

	public Date getUsrewardtime() {
		return usrewardtime;
	}

	public void setUsrewardtime(Date usrewardtime) {
		this.usrewardtime = usrewardtime;
	}

	public Double getSubjectreward() {
		return subjectreward;
	}

	public void setSubjectreward(Double subjectreward) {
		this.subjectreward = subjectreward;
	}

	public Double getUsreward() {
		return this.usreward;
	}

	public void setUsreward(Double usreward) {
		this.usreward = usreward;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getCompositescore() {
		return compositescore;
	}

	public void setCompositescore(Integer compositescore) {
		this.compositescore = compositescore;
	}

	public Integer getQcstatus() {
		return qcstatus;
	}

	public void setQcstatus(Integer qcstatus) {
		this.qcstatus = qcstatus;
	}

	public Long getQcid() {
		return qcid;
	}

	public void setQcid(Long qcid) {
		this.qcid = qcid;
	}

	public CmcCtQc getCmcCtQc() {
		return cmcCtQc;
	}

	public void setCmcCtQc(CmcCtQc cmcCtQc) {
		this.cmcCtQc = cmcCtQc;
	}

	public List<CmcCtUseranswer> getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(List<CmcCtUseranswer> userAnswers) {
		this.userAnswers = userAnswers;
	}
}