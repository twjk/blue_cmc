package com.qcmz.cmc.ws.provide.vo;

public class LocalZhaopinJobQueryBean {
	/**
	 * 用户编号 not null
	 */
	private Long uid;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 岗位
	 */
	private String title;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
