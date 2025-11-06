package com.qcmz.cmc.entity;

/**
 * CmcPkgSentence entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcPkgSentence implements java.io.Serializable {

	// Fields

	private Long sentid;
	private Long themeid;
	private CmcPkgTheme cmcPkgTheme;
	private String cat;
	private String fromlang;
	private String src;
	private String tolang;
	private String dst;
	private String voice;
	private Long libid;

	// Constructors

	/** default constructor */
	public CmcPkgSentence() {
	}

	// Property accessors

	public Long getThemeid() {
		return themeid;
	}

	public void setThemeid(Long themeid) {
		this.themeid = themeid;
	}

	public CmcPkgTheme getCmcPkgTheme() {
		return this.cmcPkgTheme;
	}

	public void setCmcPkgTheme(CmcPkgTheme cmcPkgTheme) {
		this.cmcPkgTheme = cmcPkgTheme;
	}

	public String getCat() {
		return this.cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
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

	public String getVoice() {
		return this.voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public Long getLibid() {
		return libid;
	}

	public void setLibid(Long libid) {
		this.libid = libid;
	}

	public Long getSentid() {
		return sentid;
	}

	public void setSentid(Long sentid) {
		this.sentid = sentid;
	}
}