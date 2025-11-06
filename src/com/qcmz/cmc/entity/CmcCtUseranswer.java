package com.qcmz.cmc.entity;

/**
 * CmcCtUseranswer entity. @author MyEclipse Persistence Tools
 */

public class CmcCtUseranswer implements java.io.Serializable {

	// Fields

	private Long uaid;
	private String utid;
	private CmcCtUsertask cmcCtUsertask;
	private Integer tasktype;
	private Long taskid;
	private CmcCtTask cmcCtTask;
	private Long subjectid;
	private CmcCtSubject cmcCtSubject;
	private Long optionid;
	private CmcCtOption cmcCtOption;
	private String optionvalue;
	private String morecontent;
	private String voice;
	private Integer voiceduration;
	private String pic;
	private String dst;
	private Integer score;

	// Constructors

	/** default constructor */
	public CmcCtUseranswer() {
	}

	// Property accessors
	public Long getUaid() {
		return uaid;
	}

	public void setUaid(Long uaid) {
		this.uaid = uaid;
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

	public Long getOptionid() {
		return optionid;
	}

	public void setOptionid(Long optionid) {
		this.optionid = optionid;
	}

	public CmcCtOption getCmcCtOption() {
		return this.cmcCtOption;
	}

	public void setCmcCtOption(CmcCtOption cmcCtOption) {
		this.cmcCtOption = cmcCtOption;
	}

	public String getOptionvalue() {
		return optionvalue;
	}

	public void setOptionvalue(String optionvalue) {
		this.optionvalue = optionvalue;
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

	public String getMorecontent() {
		return this.morecontent;
	}

	public void setMorecontent(String morecontent) {
		this.morecontent = morecontent;
	}

	public String getVoice() {
		return this.voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public Integer getVoiceduration() {
		return voiceduration;
	}

	public void setVoiceduration(Integer voiceduration) {
		this.voiceduration = voiceduration;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}