package com.qcmz.cmc.proxy.spider.booking;

import java.util.ArrayList;
import java.util.List;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BookingPageBean {
	private int pageCount;
	private List<BookingHotelBean> resultList = new ArrayList<BookingHotelBean>();
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<BookingHotelBean> getResultList() {
		return resultList;
	}
	public void setResultList(List<BookingHotelBean> resultList) {
		this.resultList = resultList;
	}
}
