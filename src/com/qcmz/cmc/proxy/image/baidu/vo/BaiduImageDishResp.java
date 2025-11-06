package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageDishResp extends BaiduImageResp{
	private List<BaiduImageDishBean> result;

	public List<BaiduImageDishBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageDishBean> result) {
		this.result = result;
	}
}
