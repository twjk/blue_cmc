package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.framework.util.log.BeanDesc;

/**
 * CmcPkgDaysentence entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcPkgDaysentence implements java.io.Serializable {
	// Fields
	@BeanDesc(desc="编号")
	private Long sentid;
	@BeanDesc(desc="标题")
	private String title;
	@BeanDesc(desc="源语言")
	private String fromlang;
	@BeanDesc(desc="原文")
	private String src;
	@BeanDesc(desc="目标语言")
	private String tolang;
	@BeanDesc(desc="译文")
	private String dst;
	@BeanDesc(desc="小图")
	private String smallpic;
	@BeanDesc(desc="图片")
	private String pic;
	@BeanDesc(desc="来源")
	private String source;
	private String htmlurl;
	@BeanDesc(desc="发布时间")
	private Date releasetime;
	@BeanDesc(desc="状态")
	private Integer status;
	private Date pushtime;
	private Integer pushstatus;

	// Constructors

	/** default constructor */
	public CmcPkgDaysentence() {
	}

	// Property accessors

	public Long getSentid() {
		return this.sentid;
	}

	public void setSentid(Long sentid) {
		this.sentid = sentid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSmallpic() {
		return smallpic;
	}

	public void setSmallpic(String smallpic) {
		this.smallpic = smallpic;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getReleasetime() {
		return this.releasetime;
	}

	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPushtime() {
		return pushtime;
	}

	public void setPushtime(Date pushtime) {
		this.pushtime = pushtime;
	}

	public Integer getPushstatus() {
		return pushstatus;
	}

	public void setPushstatus(Integer pushstatus) {
		this.pushstatus = pushstatus;
	}

	public String getHtmlurl() {
		return htmlurl;
	}

	public void setHtmlurl(String htmlurl) {
		this.htmlurl = htmlurl;
	}
}