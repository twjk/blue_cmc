package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class TransLibDownloadResp extends Response {
	private TransLibDownloadBean result = null;

	public TransLibDownloadBean getResult() {
		return result;
	}

	public void setResult(TransLibDownloadBean result) {
		this.result = result;
	}
}
