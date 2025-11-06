package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageCurrencyResp extends BaiduImageResp{
	private List<BaiduImageCurrencyBean> result;

	public List<BaiduImageCurrencyBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageCurrencyBean> result) {
		this.result = result;
	}
}
