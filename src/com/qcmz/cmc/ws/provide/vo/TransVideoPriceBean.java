package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：翻译价格信息
 * 修改历史：
 */
public class TransVideoPriceBean {
	/**
	 * 价格编号
	 */
	private Long priceId;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 原价，元
	 */
	private Double oriPrice;
	/**
	 * 售价，元
	 */
	private Double price;
	/**
	 * 起步价
	 */
	private Double startPrice;
	/**
	 * 服务概述
	 */
	private String overview;
	/**
	 * 服务条款
	 */
	private String service;
	/**
	 * 当前是否在工作时间内
	 */
	private boolean inWorkTime;
	/**
	 * 处理时间段HH:mm-HH:mm
	 */
	private String workTime;
	/**
	 * 处理时间说明
	 */
	private String workTimeDesc;
	
	public Long getPriceId() {
		return priceId;
	}
	public void setPriceId(Long priceId) {
		this.priceId = priceId;
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
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public boolean isInWorkTime() {
		return inWorkTime;
	}
	public void setInWorkTime(boolean inWorkTime) {
		this.inWorkTime = inWorkTime;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getWorkTimeDesc() {
		return workTimeDesc;
	}
	public void setWorkTimeDesc(String workTimeDesc) {
		this.workTimeDesc = workTimeDesc;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
}
