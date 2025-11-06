package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageLandmarkResp extends BaiduImageResp{
	private List<BaiduImageLandmarkBean> result;

	public List<BaiduImageLandmarkBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageLandmarkBean> result) {
		this.result = result;
	}
}
