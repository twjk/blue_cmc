package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageCarLocationBean {
	/**
	 * 车辆区域离左边界的距离
	 */
	private Integer left;
	/**
	 * 车辆区域离上边界的距离
	 */
	private Integer top;
	/**
	 * 车辆区域的宽度
	 */
	private Integer width;
	/**
	 * 车辆区域的高度
	 */
	private Integer height;
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
}
