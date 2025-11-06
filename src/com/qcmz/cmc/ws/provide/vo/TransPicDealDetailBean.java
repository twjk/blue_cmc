package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：翻译图片详细信息
 * 修改历史：
 */
public class TransPicDealDetailBean extends OrderDealDetailBean{
	/**
	 * 图片编号
	 */
	private String picId;	
	/**
	 * 翻译途径
	 */
	private String transWay;
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
	 * 图片地址
	 */
	private String picUrl;	
	/**
	 * 译文
	 */
	private List<TransPicTransBean> trans = new ArrayList<TransPicTransBean>();
	
	public TransPicDealDetailBean() {
		super();
	}
	public TransPicDealDetailBean(OrderDealDetailBean orderDealBean) {
		super(orderDealBean);
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}	
	public String getTransWay() {
		return transWay;
	}
	public void setTransWay(String transWay) {
		this.transWay = transWay;
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}	
	public List<TransPicTransBean> getTrans() {
		return trans;
	}
	public void setTrans(List<TransPicTransBean> trans) {
		this.trans = trans;
	}	
}