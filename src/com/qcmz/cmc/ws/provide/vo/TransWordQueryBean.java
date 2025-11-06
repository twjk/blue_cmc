package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransWordQueryBean {
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 国家代码
	 */
	private String countryName;
	/**
	 * 次数下限（含）
	 */
	private Long countFloor;
	/**
	 * 次数上限（含）
	 */
	private Long countCeiling;
	
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Long getCountFloor() {
		return countFloor;
	}
	public void setCountFloor(Long countFloor) {
		this.countFloor = countFloor;
	}
	public Long getCountCeiling() {
		return countCeiling;
	}
	public void setCountCeiling(Long countCeiling) {
		this.countCeiling = countCeiling;
	}
}
