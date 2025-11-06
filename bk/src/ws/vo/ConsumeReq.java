package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ConsumeReq extends Request {
	/**
	 * 消费信息
	 */
	private ConsumeAddBean consume = new ConsumeAddBean();

	public ConsumeAddBean getConsume() {
		return consume;
	}

	public void setConsume(ConsumeAddBean consume) {
		this.consume = consume;
	}
}
