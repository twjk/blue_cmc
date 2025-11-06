package com.qcmz.cmc.ws.provide.vo;

public class TransComboPackageBean {
	/**
	 * 套餐包编号
	 */
	private Long pkgId;
	/**
	 * 套餐编号
	 */
	private Long comboId;
	/**
	 * 套餐包标题
	 */
	private String pkgTitle;
	/**
	 * 套餐包概要
	 */
	private String pkgOverview;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 原价
	 */
	private Double oriPrice;
	/**
	 * 售价
	 */
	private Double price;
	/**
	 * 每人限购次数
	 */
	private Integer perBuyTimes;
	public Long getPkgId() {
		return pkgId;
	}
	public void setPkgId(Long pkgId) {
		this.pkgId = pkgId;
	}
	public Long getComboId() {
		return comboId;
	}
	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}
	public String getPkgOverview() {
		return pkgOverview;
	}
	public void setPkgOverview(String pkgOverview) {
		this.pkgOverview = pkgOverview;
	}
	public String getPkgTitle() {
		return pkgTitle;
	}
	public void setPkgTitle(String pkgTitle) {
		this.pkgTitle = pkgTitle;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public Integer getPerBuyTimes() {
		return perBuyTimes;
	}
	public void setPerBuyTimes(Integer perBuyTimes) {
		this.perBuyTimes = perBuyTimes;
	}		
}
