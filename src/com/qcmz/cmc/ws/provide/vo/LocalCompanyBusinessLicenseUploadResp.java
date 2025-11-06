package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class LocalCompanyBusinessLicenseUploadResp extends Response {
	private LocalCompanyBusinessLicenseUploadResult result;

	public LocalCompanyBusinessLicenseUploadResult getResult() {
		return result;
	}

	public void setResult(LocalCompanyBusinessLicenseUploadResult result) {
		this.result = result;
	}
}
