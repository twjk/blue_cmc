package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：添加视频口译订单请求
 * 修改历史：
 */
public class TransVideoAddReq extends Request {
	/**
	 * 视频口译信息
	 */
	private TransVideoAddBean bean = new TransVideoAddBean();

	public TransVideoAddBean getBean() {
		return bean;
	}

	public void setBean(TransVideoAddBean bean) {
		this.bean = bean;
	}
}