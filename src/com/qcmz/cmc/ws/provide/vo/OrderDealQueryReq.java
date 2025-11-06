package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：订单处理查询
 * 修改历史：
 */
public class OrderDealQueryReq extends Request {
	private OrderDealQueryBean bean = new OrderDealQueryBean();

	public OrderDealQueryBean getBean() {
		return bean;
	}

	public void setBean(OrderDealQueryBean bean) {
		this.bean = bean;
	}
	
}
