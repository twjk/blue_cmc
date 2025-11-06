package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：图片翻译价格信息
 * 修改历史：
 */
public class TransPicPriceBean {
	/**
	 * 服务条款
	 */
	private String service;
	/**
	 * 原价，元
	 */
	private Double oriprice;
	/**
	 * 售价，元
	 */
	private Double price;
	/**
	 * 最大字符数
	 */
	private int maxLength;
	/**
	 * 是否营业
	 */
	private boolean open;
	/**
	 * 营业时间HH:mm-HH:mm
	 */
	private String openTime;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Double getOriprice() {
		return oriprice;
	}
	public void setOriprice(Double oriprice) {
		this.oriprice = oriprice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
}
