package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

public class TransLogAddBean {
	/**
	 * 注册用户编号
	 */
	private Long uid;
	/**
	 * 推送注册编号
	 */
	private String pushRegid;
	/**
	 * 用户标识
	 */
	private String uuid;
	/**
	 * 翻译机编码
	 */
	private String tmcode;
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 会话标识
	 */
	private String sid;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 开始翻译时间
	 */
	private Date startTime;
	/**
	 * 完成翻译时间
	 */
	private Date endTime;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getPushRegid() {
		return pushRegid;
	}
	public void setPushRegid(String pushRegid) {
		this.pushRegid = pushRegid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
	public String getTmcode() {
		return tmcode;
	}
	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
}
