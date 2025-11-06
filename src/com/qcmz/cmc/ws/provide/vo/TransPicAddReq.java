package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：添加翻译图片请求
 * 修改历史：
 */
public class TransPicAddReq extends Request {
	/**
	 * 图片信息
	 */
	private TransPicAddBean bean = new TransPicAddBean();

	public TransPicAddBean getBean() {
		return bean;
	}

	public void setBean(TransPicAddBean bean) {
		this.bean = bean;
	}
}