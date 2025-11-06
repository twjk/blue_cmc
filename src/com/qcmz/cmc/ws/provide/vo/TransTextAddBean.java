package com.qcmz.cmc.ws.provide.vo;

public class TransTextAddBean extends OrderCreateBean{	
	/**
	 * 推送注册编号
	 */
	private String pushRegid;
	/**
	 * 翻译模式，1自由模式2陪伴模式
	 */
	private Integer transModel;
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
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	
	
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
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPushRegid() {
		return pushRegid;
	}
	public void setPushRegid(String pushRegid) {
		this.pushRegid = pushRegid;
	}
	public Integer getTransModel() {
		return transModel;
	}
	public void setTransModel(Integer transModel) {
		this.transModel = transModel;
	}
}
