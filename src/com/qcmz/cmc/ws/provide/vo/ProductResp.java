package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class ProductResp extends Response {
	private ProductBean result;

	public ProductBean getResult() {
		return result;
	}

	public void setResult(ProductBean result) {
		this.result = result;
	}
}
