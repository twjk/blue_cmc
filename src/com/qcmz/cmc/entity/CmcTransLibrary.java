package com.qcmz.cmc.entity;

/**
 * CmcTransLibrary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTransLibrary implements java.io.Serializable {

	// Fields

	private Long libid;
	private String libtype;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private Integer twoway;
	private String cat;
	private String sourceid;
	private String voice;
	private Integer ttssrc;
	private String ttstext;
    private String remark;
	private Long hitcount;
	private Integer similar;
	private Integer status;
	private Integer checkstatus;

	// Constructors

	/** default constructor */
	public CmcTransLibrary() {
	}

	// Property accessors

	public Long getLibid() {
		return this.libid;
	}

	public void setLibid(Long libid) {
		this.libid = libid;
	}

	public String getLibtype() {
		return this.libtype;
	}

	public void setLibtype(String libtype) {
		this.libtype = libtype;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public Integer getTwoway() {
		return twoway;
	}

	public void setTwoway(Integer twoway) {
		this.twoway = twoway;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public Long getHitcount() {
		return hitcount;
	}

	public void setHitcount(Long hitcount) {
		this.hitcount = hitcount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(Integer checkstatus) {
		this.checkstatus = checkstatus;
	}

	public Integer getSimilar() {
		return similar;
	}

	public void setSimilar(Integer similar) {
		this.similar = similar;
	}

	public Integer getTtssrc() {
		return ttssrc;
	}

	public void setTtssrc(Integer ttssrc) {
		this.ttssrc = ttssrc;
	}
	public String getTtstext() {
		return ttstext;
	}

	public void setTtstext(String ttstext) {
		this.ttstext = ttstext;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}