package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class DaySentenceBean {
	/**
	 * 语句编号
	 */
	private Long sentenceId;
	/**
	 * 日期，格式yyyy-mm-dd
	 */
	private String date;
	/**
	 * 日期，格式 09 Jul.2015
	 */
	private String date2;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 源语言名称
	 */
	private String fromName;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 目标语言名称
	 */
	private String toName;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 图片地址
	 */
	private String pic;
	/**
	 * 静态页面地址
	 */
	private String htmlUrl;
	
	public DaySentenceBean() {
		super();
	}
	
	public DaySentenceBean(Long sentenceId) {
		super();
		this.sentenceId = sentenceId;
	}

	public DaySentenceBean(Long sentenceId, Date date, String title, String from, String src, String to,
			String dst, String source, String pic, String htmlUrl) {
		super();
		this.sentenceId = sentenceId;
		this.date = DateUtil.formatDate(date);
		this.date2 = CmcUtil.formatReleaseTime(this.date);
		this.title = title;
		this.from = from;
		this.src = src;
		this.to = to;
		this.dst = dst;
		this.source = source;
		this.pic = pic;
		this.htmlUrl = htmlUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DaySentenceBean other = (DaySentenceBean) obj;
		if (sentenceId == null) {
			if (other.sentenceId != null)
				return false;
		} else if (!sentenceId.equals(other.sentenceId))
			return false;
		return true;
	}

	public Long getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(Long sentenceId) {
		this.sentenceId = sentenceId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
}
