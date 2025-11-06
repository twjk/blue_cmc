package com.qcmz.cmc.ws.provide.vo;

public class ProductItemBean {
	/**
	 * 商品编号
	 */
	private Long itemId;
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 苹果内付费标识
	 */
	private String iapId;
	/**
	 * 原价，元
	 */
	private Double oriPrice;
	/**
	 * 售价，元
	 */
	private Double price;
	/**
	 * 价格类型，1打包价2单价
	 */
	private Integer priceType;
	/**
	 * 阶梯计费最小数量
	 */
	private Integer minNum;
	/**
	 * 阶梯计费最大数量
	 */
	private Integer maxNum;
	/**
	 * 商品图片
	 */
	private String pic;
	/**
	 * 标语
	 */
	private String slogan;
	/**
	 * 商品概述
	 */
	private String overview;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getIapId() {
		return iapId;
	}
	public void setIapId(String iapId) {
		this.iapId = iapId;
	}
	public Double getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(Double oriPrice) {
		this.oriPrice = oriPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public Integer getMinNum() {
		return minNum;
	}
	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
}
