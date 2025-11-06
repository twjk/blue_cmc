package com.qcmz.cmc.entity;

/**
 * CmcLtCompany entity. @author MyEclipse Persistence Tools
 */

public class CmcLtCompany implements java.io.Serializable {

	// Fields

	private Long companyid;
	private String companyname;
	private Long userid = 0L;
	private String logo;
	private String introduce;
	private String uscc;
	private String contacts;
	private String tel;
	private String businesslicense;
	private String hr;
	private String hrtel;
	private Integer certifystatus = 0;
	private String certifyresult;
	private Integer createway = 1;

	// Constructors

	/** default constructor */
	public CmcLtCompany() {
	}

	public CmcLtCompany(String companyname) {
		super();
		this.companyname = companyname;
	}

	// Property accessors

	public Long getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getUscc() {
		return this.uscc;
	}

	public void setUscc(String uscc) {
		this.uscc = uscc;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBusinesslicense() {
		return this.businesslicense;
	}

	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getHrtel() {
		return hrtel;
	}

	public void setHrtel(String hrtel) {
		this.hrtel = hrtel;
	}

	public Integer getCertifystatus() {
		return certifystatus;
	}

	public void setCertifystatus(Integer certifystatus) {
		this.certifystatus = certifystatus;
	}

	public String getCertifyresult() {
		return certifyresult;
	}

	public void setCertifyresult(String certifyresult) {
		this.certifyresult = certifyresult;
	}

	public Integer getCreateway() {
		return createway;
	}

	public void setCreateway(Integer createway) {
		this.createway = createway;
	}
}