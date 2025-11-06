package com.qcmz.cmc.proxy.spider.booking;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BookingCityBean {
	private String cityId;
	private String cityName;
	public BookingCityBean() {
		super();
	}
	public BookingCityBean(String cityId, String cityName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
