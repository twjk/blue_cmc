package com.qcmz.cmc.ws.provide.vo;

public class OrderItemBean {
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 源语言代码
	 */
	private String from;
	/**
	 * 目标语言代码
	 */
	private String to;
	/**
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 原价单价
	 */
	private Double oriPrice;
	/**
	 * 售价单价
	 */
	private Double price;
	/**
	 * 商品数量
	 */
	private int itemNum;
	/**
	 * 商品有效期起始时间，空表示不限
	 */
	private Long startTime;
	/**
	 * 商品有效期结束时间，空表示不限
	 */
	private Long endTime;
	/**
	 * 关联编号
	 */
	private Long refId;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public Double getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(Double oriPrice) {
		this.oriPrice = oriPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
}
