package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageCurrencyBean {
	/**
	 * 货币名称，无法识别返回空
	 */
	private String currencyName;
	/**
	 * 是否返回详细信息，含有返回1，不含有返回0
	 */
	private Integer hasdetail;
	/**
	 * 货币代码，SGD
	 */
	private String currencyCode;
	/**
	 * 货币面值，50元
	 */
	private String currencyDenomination;
	/**
	 * 货币年份，2004年
	 */
	private String year;
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Integer getHasdetail() {
		return hasdetail;
	}
	public void setHasdetail(Integer hasdetail) {
		this.hasdetail = hasdetail;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyDenomination() {
		return currencyDenomination;
	}
	public void setCurrencyDenomination(String currencyDenomination) {
		this.currencyDenomination = currencyDenomination;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
