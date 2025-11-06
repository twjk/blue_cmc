package com.qcmz.cmc.entity;

/**
 * CmcPkgTheme entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcPkgTheme implements java.io.Serializable {

	// Fields

	private Long themeid;
	private String themecode;
	private String titlecn;
	private String titlelc;
	private String pic;
	private String bgpic;
	private String downloadurl;
	private String filesize;
	private Integer status;
	private Integer sortindex;

	// Constructors

	/** default constructor */
	public CmcPkgTheme() {
	}
	// Property accessors

	public Long getThemeid() {
		return this.themeid;
	}

	public void setThemeid(Long themeid) {
		this.themeid = themeid;
	}

	public String getThemecode() {
		return themecode;
	}

	public void setThemecode(String themecode) {
		this.themecode = themecode;
	}

	public String getTitlecn() {
		return this.titlecn;
	}

	public void setTitlecn(String titlecn) {
		this.titlecn = titlecn;
	}

	public String getTitlelc() {
		return this.titlelc;
	}

	public void setTitlelc(String titlelc) {
		this.titlelc = titlelc;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getBgpic() {
		return this.bgpic;
	}

	public void setBgpic(String bgpic) {
		this.bgpic = bgpic;
	}

	public String getDownloadurl() {
		return this.downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public String getFilesize() {
		return this.filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

}