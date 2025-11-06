package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageAnimalResp extends BaiduImageResp{
	private List<BaiduImageAnimalBean> result;

	public List<BaiduImageAnimalBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageAnimalBean> result) {
		this.result = result;
	}
}
