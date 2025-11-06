package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class OrderPrepayReq extends Request {
	private OrderPrepayBean bean = new OrderPrepayBean();

	public OrderPrepayBean getBean() {
		return bean;
	}

	public void setBean(OrderPrepayBean bean) {
		this.bean = bean;
	}
}
