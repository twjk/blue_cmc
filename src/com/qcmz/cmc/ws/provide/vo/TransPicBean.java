package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

/**
 * 类说明：翻译图片信息
 * 修改历史：
 */
public class TransPicBean {
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 用户编号
	 */
	private Long uid;
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
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 金额，元
	 */
	private Double amount;
	/**
	 * 状态
	 */
	private String dealStatus;
	/**
	 * 翻译结果
	 */
	private String transResult;
	
	public TransPicBean() {
		super();
	}
	
	public TransPicBean(String picId, Long uid, String transWay, String from, String to, String picUrl, String thumbUrl,
			Date createTime, String address, Double amount, String dealStatus) {
		super();
		this.picId = picId;
		this.uid = uid;
		this.transWay = transWay;
		this.from = from;
		this.to = to;
		this.picUrl = (thumbUrl==null||"".equals(thumbUrl.trim()))?picUrl:thumbUrl;
		this.createTime = createTime.getTime();
		this.address = address;
		this.amount = amount;
		this.dealStatus = dealStatus;
	}

	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransResult() {
		return transResult;
	}
	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}
}