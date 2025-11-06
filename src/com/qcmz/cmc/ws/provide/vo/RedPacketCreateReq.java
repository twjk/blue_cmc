package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RedPacketCreateReq extends Request {
	private RedPacketCreateBean bean = new RedPacketCreateBean();

	public RedPacketCreateBean getBean() {
		return bean;
	}

	public void setBean(RedPacketCreateBean bean) {
		this.bean = bean;
	}
	
}
