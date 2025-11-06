package com.qcmz.cmc.ws.provide.vo;

public class LocalCompanyBasicBean {
	/**
	 * 公司编号
	 */
	private Long companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 招聘联系人
	 */
	private String hr;
	/**
	 * 招聘联系人电话
	 */
	private String hrTel;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
