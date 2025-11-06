package com.qcmz.cmc.ws.provide.vo;

public class UserTransComboAddBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 套餐编号
	 */
	private Long comboId;
	/**
	 * 套餐包编号
	 */
	private Long pkgId;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 起始时间
	 */
	private Long startTime;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getComboId() {
		return comboId;
	}
	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}
	public Long getPkgId() {
		return pkgId;
	}
	public void setPkgId(Long pkgId) {
		this.pkgId = pkgId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	
}
