package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：获取翻译图片详细信息返回
 * 修改历史：
 */
public class TransPicDealDetailResp extends Response {
	private TransPicDealDetailBean result;

	public TransPicDealDetailBean getResult() {
		return result;
	}

	public void setResult(TransPicDealDetailBean result) {
		this.result = result;
	}
}
