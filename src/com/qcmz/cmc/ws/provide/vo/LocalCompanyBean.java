package com.qcmz.cmc.ws.provide.vo;

public class LocalCompanyBean {
	/**
	 * 公司编号
	 */
	private Long companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 统一社会信用代码
	 */
	private String uscc;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 营业执照
	 */
	private String businessLicense;
	/**
	 * 公司认证状态
	 */
	private Integer certifyStatus;
	/**
	 * 公司认证结果描述
	 */
	private String certifyResult;
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
	public String getUscc() {
		return uscc;
	}
	public void setUscc(String uscc) {
		this.uscc = uscc;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public Integer getCertifyStatus() {
		return certifyStatus;
	}
	public void setCertifyStatus(Integer certifyStatus) {
		this.certifyStatus = certifyStatus;
	}
	public String getCertifyResult() {
		return certifyResult;
	}
	public void setCertifyResult(String certifyResult) {
		this.certifyResult = certifyResult;
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
