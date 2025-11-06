package com.qcmz.cmc.entity;

/**
 * CmcDubber entity. @author MyEclipse Persistence Tools
 */

public class CmcDubber implements java.io.Serializable {

	// Fields

	private Long dubberid;
	private Long catid;
	private String fullcatid;
	private String title;
	private String fullname;
	private Long itemid;
	private String gender;
	private String icon;
	private String specialty;
	private String style;
	private String audition;
	private Integer sortindex;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcDubber() {
	}

	/** minimal constructor */
	public CmcDubber(String title, Integer sortindex, Integer status) {
		this.title = title;
		this.sortindex = sortindex;
		this.status = status;
	}

	/** full constructor */
	public CmcDubber(Long catid, String fullcatid, String title, String fullname, Long itemid, String gender,
			String icon, String specialty, String style, String audition, Integer sortindex, Integer status) {
		this.catid = catid;
		this.fullcatid = fullcatid;
		this.title = title;
		this.fullname = fullname;
		this.itemid = itemid;
		this.gender = gender;
		this.icon = icon;
		this.specialty = specialty;
		this.style = style;
		this.audition = audition;
		this.sortindex = sortindex;
		this.status = status;
	}

	// Property accessors

	public Long getDubberid() {
		return this.dubberid;
	}

	public void setDubberid(Long dubberid) {
		this.dubberid = dubberid;
	}

	public Long getCatid() {
		return this.catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public String getFullcatid() {
		return this.fullcatid;
	}

	public void setFullcatid(String fullcatid) {
		this.fullcatid = fullcatid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Long getItemid() {
		return this.itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAudition() {
		return this.audition;
	}

	public void setAudition(String audition) {
		this.audition = audition;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}