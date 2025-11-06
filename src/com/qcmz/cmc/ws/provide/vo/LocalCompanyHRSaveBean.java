package com.qcmz.cmc.ws.provide.vo;

public class LocalCompanyHRSaveBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 招聘联系人
	 */
	private String hr;
	/**
	 * 招聘联系人电话
	 */
	private String hrTel;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getHr() {
		return hr;
	}
	public void setHr(String hr) {
		this.hr = hr;
	}
	public String getHrTel() {
		return hrTel;
	}
	public void setHrTel(String hrTel) {
		this.hrTel = hrTel;
	}
}
