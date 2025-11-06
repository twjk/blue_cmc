package com.qcmz.cmc.proxy.spider.booking;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BookingHotelBean {
	private String hotelName;
	private String langCode;
	private String url;
	public BookingHotelBean() {
		super();
	}
	public BookingHotelBean(String hotelName, String url) {
		super();
		this.hotelName = hotelName;
		this.url = url;
	}
	public BookingHotelBean(String hotelName, String langCode, String url) {
		super();
		this.hotelName = hotelName;
		this.langCode = langCode;
		this.url = url;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
