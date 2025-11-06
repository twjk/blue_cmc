package com.qcmz.cmc.ws.provide.vo;

public class TransUserCorrectBean {
	/**
	 * 注册用户编号
	 */
	private Long uid;
	/**
	 * 用户标识
	 */
	private String uuid;
	/**
	 * 推送注册编号
	 */
	private String pushRegid;
	/**
	 * 会话标识
	 */
	private String sid;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 待翻译内容
	 */
	private String src;
	/**
	 * 修正译文
	 */
	private String dst;
	/**
	 * 机器翻译译文
	 */
	private String mtDst;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPushRegid() {
		return pushRegid;
	}
	public void setPushRegid(String pushRegid) {
		this.pushRegid = pushRegid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getMtDst() {
		return mtDst;
	}
	public void setMtDst(String mtDst) {
		this.mtDst = mtDst;
	}
}
