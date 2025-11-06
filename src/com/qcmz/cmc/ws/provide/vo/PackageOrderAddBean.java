package com.qcmz.cmc.ws.provide.vo;

public class PackageOrderAddBean extends OrderCreateBean{
	/**
	 * 活动编号
	 */
	private Long actId;
	/**
	 * 起始时间
	 */
	private Long startTime;
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}	
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
}
