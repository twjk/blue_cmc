package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ProxyAccountCallAddReq extends Request {
	private ProxyAccountCallAddBean bean = new ProxyAccountCallAddBean();

	public ProxyAccountCallAddBean getBean() {
		return bean;
	}
	public void setBean(ProxyAccountCallAddBean bean) {
		this.bean = bean;
	}
}
