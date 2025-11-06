package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalCompanyHRSaveReq extends Request {
	private LocalCompanyHRSaveBean bean = new LocalCompanyHRSaveBean();

	public LocalCompanyHRSaveBean getBean() {
		return bean;
	}

	public void setBean(LocalCompanyHRSaveBean bean) {
		this.bean = bean;
	}
}
