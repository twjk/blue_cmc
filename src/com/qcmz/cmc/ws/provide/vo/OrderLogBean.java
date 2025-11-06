package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

public class OrderLogBean {
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 日志
	 */
	private String log;
	/**
	 * 操作时间
	 */
	private Long operTime;
	/**
	 * 操作人
	 */
	private String operName;
	
	public OrderLogBean() {
		super();
	}
	
	public OrderLogBean(String dealStatus) {
		super();
		this.dealStatus = dealStatus;
	}

	public OrderLogBean(String dealStatus, String log, Date operTime, String operName) {
		super();
		this.dealStatus = dealStatus;
		this.log = log;
		this.operTime = operTime.getTime();
		this.operName = operName;
	}

	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public Long getOperTime() {
		return operTime;
	}
	public void setOperTime(Long operTime) {
		this.operTime = operTime;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
}
