package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImagePlantResp extends BaiduImageResp{
	private List<BaiduImagePlantBean> result;

	public List<BaiduImagePlantBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImagePlantBean> result) {
		this.result = result;
	}
}
