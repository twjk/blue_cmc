package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class OrderMsgAddReq extends Request {
	private OrderMsgAddBean bean = new OrderMsgAddBean();

	public OrderMsgAddBean getBean() {
		return bean;
	}

	public void setBean(OrderMsgAddBean bean) {
		this.bean = bean;
	}
}
