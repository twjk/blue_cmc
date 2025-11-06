package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageGeneralResp extends BaiduImageResp{
	private List<BaiduImageGeneralBean> result;

	public List<BaiduImageGeneralBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageGeneralBean> result) {
		this.result = result;
	}
}
