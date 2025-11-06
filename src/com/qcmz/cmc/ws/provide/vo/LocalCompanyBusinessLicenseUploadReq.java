package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalCompanyBusinessLicenseUploadReq extends Request {
	private LocalCompanyBusinessLicenseUploadBean bean = new LocalCompanyBusinessLicenseUploadBean();

	public LocalCompanyBusinessLicenseUploadBean getBean() {
		return bean;
	}

	public void setBean(LocalCompanyBusinessLicenseUploadBean bean) {
		this.bean = bean;
	}
}
