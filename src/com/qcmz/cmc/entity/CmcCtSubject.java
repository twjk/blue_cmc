package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * CmcCtSubject entity. @author MyEclipse Persistence Tools
 */

public class CmcCtSubject implements java.io.Serializable {

	// Fields
	private Long subjectid;
	private Long libid;	
	private CmcCtLib cmcCtLib;
	private Integer subjectcat;
	private Integer subjecttype;
	private Integer passscore;
	private Double subjectreward;
	private String fromlang;
	private String tolang;
	private Integer contenttype;
	private String content;
	private Integer recordtimes;
	private String audio;
	private String pic;
	private String video;
	private Integer videosource;
	private String adparam;
	private Integer status;
	private List<CmcCtOption> options = new ArrayList<CmcCtOption>(0);

	// Constructors

	/** default constructor */
	public CmcCtSubject() {
	}

	// Property accessors

	public Long getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Long subjectid) {
		this.subjectid = subjectid;
	}

	public Long getLibid() {
		return libid;
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

	public Integer getSubjectcat() {
		return this.subjectcat;
	}

	public void setSubjectcat(Integer subjectcat) {
		this.subjectcat = subjectcat;
	}

	public Integer getSubjecttype() {
		return this.subjecttype;
	}

	public void setSubjecttype(Integer subjecttype) {
		this.subjecttype = subjecttype;
	}

	public Integer getPassscore() {
		return passscore;
	}

	public void setPassscore(Integer passscore) {
		this.passscore = passscore;
	}

	public Double getSubjectreward() {
		return this.subjectreward;
	}

	public void setSubjectreward(Double subjectreward) {
		this.subjectreward = subjectreward;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public Integer getContenttype() {
		return contenttype;
	}

	public void setContenttype(Integer contenttype) {
		this.contenttype = contenttype;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRecordtimes() {
		return this.recordtimes;
	}

	public void setRecordtimes(Integer recordtimes) {
		this.recordtimes = recordtimes;
	}

	public String getAudio() {
		return this.audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Integer getVideosource() {
		return videosource;
	}

	public void setVideosource(Integer videosource) {
		this.videosource = videosource;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAdparam() {
		return adparam;
	}

	public void setAdparam(String adparam) {
		this.adparam = adparam;
	}

	public List<CmcCtOption> getOptions() {
		return options;
	}

	public void setOptions(List<CmcCtOption> options) {
		this.options = options;
	}

}