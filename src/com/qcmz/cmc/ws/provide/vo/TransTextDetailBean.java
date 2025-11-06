package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：文本翻译详细信息
 * 修改历史：
 */
public class TransTextDetailBean extends OrderDetailBean{
	/**
	 * 翻译模式
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
	public TransTextDetailBean() {
		super();
	}
	public TransTextDetailBean(OrderDetailBean orderDetail) {
		super(orderDetail);
	}
	public Integer getTransModel() {
		return transModel;
	}
	public void setTransModel(Integer transModel) {
		this.transModel = transModel;
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
}