package com.qcmz.cmc.entity;

/**
 * CmcCtSimilar entity. @author MyEclipse Persistence Tools
 */

public class CmcCtSimilar implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer contenttype;
	private String voice;
	private Long userid;
	private String utid;
	private Long uaid;
	private String similar;
	private Long similaruserid;
	private String similarutid;
	private Integer sameuser;

	// Constructors

	/** default constructor */
	public CmcCtSimilar() {
	}

	/** full constructor */
	public CmcCtSimilar(Integer contenttype, String voice, Long userid, String utid, Long uaid, String similar,
			Long similaruserid, String similarutid) {
		this.contenttype = contenttype;
		this.voice = voice;
		this.userid = userid;
		this.utid = utid;
		this.uaid = uaid;
		this.similar = similar;
		this.similaruserid = similaruserid;
		this.similarutid = similarutid;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getContenttype() {
		return this.contenttype;
	}

	public void setContenttype(Integer contenttype) {
		this.contenttype = contenttype;
	}

	public String getVoice() {
		return this.voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUtid() {
		return this.utid;
	}

	public void setUtid(String utid) {
		this.utid = utid;
	}

	public Long getUaid() {
		return this.uaid;
	}

	public void setUaid(Long uaid) {
		this.uaid = uaid;
	}

	public String getSimilar() {
		return this.similar;
	}

	public void setSimilar(String similar) {
		this.similar = similar;
	}

	public Long getSimilaruserid() {
		return this.similaruserid;
	}

	public void setSimilaruserid(Long similaruserid) {
		this.similaruserid = similaruserid;
	}

	public String getSimilarutid() {
		return this.similarutid;
	}

	public void setSimilarutid(String similarutid) {
		this.similarutid = similarutid;
	}

	public Integer getSameuser() {
		return sameuser;
	}

	public void setSameuser(Integer sameuser) {
		this.sameuser = sameuser;
	}
}