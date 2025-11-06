package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class PackageOrderDetailResp extends Response {
	private PackageOrderDetailBean result = null;

	public PackageOrderDetailBean getResult() {
		return result;
	}

	public void setResult(PackageOrderDetailBean result) {
		this.result = result;
	}
}
