package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class WalletAccountResp extends Response {
	private WalletAccountBean result = null;

	public WalletAccountBean getResult() {
		return result;
	}

	public void setResult(WalletAccountBean result) {
		this.result = result;
	}
}
