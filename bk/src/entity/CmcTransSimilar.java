package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcTransSimilar entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTransSimilar implements java.io.Serializable {

	// Fields

	private Long id;
	private String srclang;
	private String tolang;
	private Long proxyid;
	private Integer similar;
	private Integer similarcount;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcTransSimilar() {
	}

	/** full constructor */
	public CmcTransSimilar(String srclang, String tolang, Long proxyid,
			Integer similar, Integer similarcount, Date createtime) {
		this.srclang = srclang;
		this.tolang = tolang;
		this.proxyid = proxyid;
		this.similar = similar;
		this.similarcount = similarcount;
		this.createtime = createtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrclang() {
		return this.srclang;
	}

	public void setSrclang(String srclang) {
		this.srclang = srclang;
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public Integer getSimilar() {
		return this.similar;
	}

	public void setSimilar(Integer similar) {
		this.similar = similar;
	}

	public Integer getSimilarcount() {
		return this.similarcount;
	}

	public void setSimilarcount(Integer similarcount) {
		this.similarcount = similarcount;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}