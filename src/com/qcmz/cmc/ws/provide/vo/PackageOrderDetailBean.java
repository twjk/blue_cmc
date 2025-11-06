package com.qcmz.cmc.ws.provide.vo;

public class PackageOrderDetailBean extends OrderDetailBean{
	/**
	 * 套餐名称
	 */
	private String actTitle;
	/**
	 * 活动描述
	 */
	private String actDesc;
	/**
	 * 有效期起始时间
	 */
	private Long startTime;
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	public String getActDesc() {
		return actDesc;
	}
	public void setActDesc(String actDesc) {
		this.actDesc = actDesc;
	}	
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
}
