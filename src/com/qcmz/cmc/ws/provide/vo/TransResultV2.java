package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class TransResultV2 {
	/**
	 * 原文语言
	 */
	private String from;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文语言
	 */
	private String to;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单处理状态
	 */
	private String dealStatus;
	/**
	 * 翻译列表
	 */
	private List<TransDstBean> dsts = new ArrayList<TransDstBean>();
	/**
	 * 分词列表
	 */
	private List<TransTermBean> keywords = new ArrayList<TransTermBean>();
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public List<TransDstBean> getDsts() {
		return dsts;
	}
	public void setDsts(List<TransDstBean> dsts) {
		this.dsts = dsts;
	}
	public List<TransTermBean> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<TransTermBean> keywords) {
		this.keywords = keywords;
	}
}