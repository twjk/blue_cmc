package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：获取翻译图片详细信息返回
 * 修改历史：
 */
public class TransPicDetailResp extends Response {
	private TransPicDetailBean bean;

	public TransPicDetailBean getBean() {
		return bean;
	}

	public void setBean(TransPicDetailBean bean) {
		this.bean = bean;
	}
}
