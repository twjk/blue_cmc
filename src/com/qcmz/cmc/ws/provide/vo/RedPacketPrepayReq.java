package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RedPacketPrepayReq extends Request {
	private RedPacketPrepayBean bean = new RedPacketPrepayBean();

	public RedPacketPrepayBean getBean() {
		return bean;
	}

	public void setBean(RedPacketPrepayBean bean) {
		this.bean = bean;
	}
}
