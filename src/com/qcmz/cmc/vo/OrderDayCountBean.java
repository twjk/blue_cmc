package com.qcmz.cmc.vo;

public class OrderDayCountBean {
	private String date;
	private Long count;
	
	public OrderDayCountBean() {
		super();
	}
	public OrderDayCountBean(String date, Long count) {
		super();
		this.date = date;
		this.count = count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
