package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalCompanySaveReq extends Request {
	private LocalCompanySaveBean bean = new LocalCompanySaveBean();

	public LocalCompanySaveBean getBean() {
		return bean;
	}

	public void setBean(LocalCompanySaveBean bean) {
		this.bean = bean;
	}
}
