package com.qcmz.cmc.proxy.spider.booking;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BookingCountryBean {
	private String destId;
	private String countryName;
	private int count;
	private String show;
	public BookingCountryBean() {
		super();
	}
	public BookingCountryBean(String destId, String countryName, int count) {
		super();
		this.destId = destId;
		this.countryName = countryName;
		this.count = count;
		this.show = new StringBuilder(countryName).append("[").append(count).append("]").toString();
	}
	public String getDestId() {
		return destId;
	}
	public void setDestId(String destId) {
		this.destId = destId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
}
