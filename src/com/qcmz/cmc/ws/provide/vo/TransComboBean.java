package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class TransComboBean {
	/**
	 * 套餐编号
	 */
	private Long comboId;
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 套餐类型
	 */
	private Integer comboType;
	/**
	 * 套餐名称
	 */
	private String title;
	/**
	 * 原单价
	 */
	private Double oriUnitPrice;
	/**
	 * 单价
	 */
	private Double unitPrice;
	/**
	 * 价格单位
	 */
	private Integer priceUnit;
	/**
	 * 单位名称
	 */
	private String unitName;
	/**
	 * 服务途径
	 */
	private Integer serviceWay;
	/**
	 * 服务账号
	 */
	private String serviceAccount;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 套餐概要
	 */
	private String overview;
	/**
	 * 套餐详述
	 */
	private String description;
	/**
	 * 有效期开始时间
	 */
	private Long startTime;
	/**
	 * 有效期结束时间
	 */
	private Long endTime;
	/**
	 * 套餐包
	 */
	private List<TransComboPackageBean> pkgs = new ArrayList<TransComboPackageBean>();
	/**
	 * 图片列表
	 */
	private List<TransComboPicBean> pics = new ArrayList<TransComboPicBean>();
	/**
	 * 限定使用的语言列表，空表示均可使用
	 */
	private List<TransComboLangBean> langs = new ArrayList<TransComboLangBean>();
	/**
	 * 限定使用的场景，空表示均可使用
	 */
	private List<SceneBean> scenes = new ArrayList<SceneBean>();
	/**
	 * 标签列表
	 */
	private List<TransComboTagBean> tags = new ArrayList<TransComboTagBean>();
	
	public Long getComboId() {
		return comboId;
	}
	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Integer getComboType() {
		return comboType;
	}
	public void setComboType(Integer comboType) {
		this.comboType = comboType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getOriUnitPrice() {
		return oriUnitPrice;
	}
	public void setOriUnitPrice(Double oriUnitPrice) {
		this.oriUnitPrice = oriUnitPrice;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getServiceWay() {
		return serviceWay;
	}
	public void setServiceWay(Integer serviceWay) {
		this.serviceWay = serviceWay;
	}
	public String getServiceAccount() {
		return serviceAccount;
	}
	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<TransComboPackageBean> getPkgs() {
		return pkgs;
	}
	public void setPkgs(List<TransComboPackageBean> pkgs) {
		this.pkgs = pkgs;
	}
	public List<TransComboLangBean> getLangs() {
		return langs;
	}
	public void setLangs(List<TransComboLangBean> langs) {
		this.langs = langs;
	}
	public List<TransComboTagBean> getTags() {
		return tags;
	}
	public void setTags(List<TransComboTagBean> tags) {
		this.tags = tags;
	}
	public List<SceneBean> getScenes() {
		return scenes;
	}
	public void setScenes(List<SceneBean> scenes) {
		this.scenes = scenes;
	}
	public List<TransComboPicBean> getPics() {
		return pics;
	}
	public void setPics(List<TransComboPicBean> pics) {
		this.pics = pics;
	}
}
