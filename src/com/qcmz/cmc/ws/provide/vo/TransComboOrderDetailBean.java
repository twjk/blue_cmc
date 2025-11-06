package com.qcmz.cmc.ws.provide.vo;

public class TransComboOrderDetailBean extends OrderDetailBean{
	/**
	 * 套餐名称
	 */
	private String comboTitle;
	/**
	 * 活动描述
	 */
	private String comboDesc;
	/**
	 * 起算时间
	 */
	private Long startTime;
	/**
	 * 用户套餐信息，可能为null
	 */
	private UserTransComboBean userCombo;
	
	public String getComboTitle() {
		return comboTitle;
	}
	public void setComboTitle(String comboTitle) {
		this.comboTitle = comboTitle;
	}
	public String getComboDesc() {
		return comboDesc;
	}
	public void setComboDesc(String comboDesc) {
		this.comboDesc = comboDesc;
	}	
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public UserTransComboBean getUserCombo() {
		return userCombo;
	}
	public void setUserCombo(UserTransComboBean userCombo) {
		this.userCombo = userCombo;
	}
}
