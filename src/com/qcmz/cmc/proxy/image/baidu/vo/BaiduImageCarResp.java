package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.List;

public class BaiduImageCarResp extends BaiduImageResp{
	/**
	 * 车型识别结果
	 */
	private List<BaiduImageCarBean> result;
	/**
	 * 车身颜色
	 */
	private String color_result;
	/**
	 * 车辆在图片中的位置信息
	 */
	private BaiduImageCarLocationBean location_result;

	public List<BaiduImageCarBean> getResult() {
		return result;
	}

	public void setResult(List<BaiduImageCarBean> result) {
		this.result = result;
	}

	public String getColor_result() {
		return color_result;
	}

	public void setColor_result(String color_result) {
		this.color_result = color_result;
	}

	public BaiduImageCarLocationBean getLocation_result() {
		return location_result;
	}

	public void setLocation_result(BaiduImageCarLocationBean location_result) {
		this.location_result = location_result;
	}
}
