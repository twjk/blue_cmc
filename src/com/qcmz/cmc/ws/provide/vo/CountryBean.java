package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CountryBean {
	/**
	 * 国家代码
	 */
	private String countryCode;
	/**
	 * 国家名称
	 */
	private String countryName;
	public CountryBean() {
		super();
	}
	public CountryBean(String countryName) {
		super();
		this.countryName = countryName;
	}
	public CountryBean(String countryCode, String countryName) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
