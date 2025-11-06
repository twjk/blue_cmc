package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class OrderEvalReq extends Request {
	private OrderEvalAddBean bean = new OrderEvalAddBean();

	public OrderEvalAddBean getBean() {
		return bean;
	}

	public void setBean(OrderEvalAddBean bean) {
		this.bean = bean;
	}
}
