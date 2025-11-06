package com.qcmz.cmc.ws.provide.vo;

public class LocalCompanySaveBean {
	/**
	 * 用户编号
	 */
	private Long uid;
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
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
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
}
