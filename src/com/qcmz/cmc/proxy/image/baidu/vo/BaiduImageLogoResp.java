package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageLogoResp extends BaiduImageResp{
	private List<BaiduImageLogoBean> result;

	public List<BaiduImageLogoBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageLogoBean> result) {
		this.result = result;
	}
}
