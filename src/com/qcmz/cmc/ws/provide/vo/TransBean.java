package com.qcmz.cmc.ws.provide.vo;

/**
 * 翻译信息
 */
public class TransBean {
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
	 * 翻译机编码
	 */
	private String tmcode;
	/**
	 * 翻译类型,01机器翻译 02概要翻译 03文档翻译
	 */
	private String transType;
	/**
	 * 翻译模式，1自由模式2陪伴模式
	 */
	private Integer transModel;
	/**
	 * 渠道代码
	 */
	private String channel;
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
	public Integer getTransModel() {
		return transModel;
	}
	public void setTransModel(Integer transModel) {
		this.transModel = transModel;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
