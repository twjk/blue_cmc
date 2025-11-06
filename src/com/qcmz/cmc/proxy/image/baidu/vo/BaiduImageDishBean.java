package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageDishBean {
	/**
	 * 菜名
	 */
	private String name;
	/**
	 * 置信度，示例：0.5321
	 */
	private String probability;
	/**
	 * 
	 */
	private Boolean has_calorie;
	/**
	 * 卡路里，每100g的卡路里含量
	 */
	private String calorie;
	/**
	 * 百科信息
	 */
	private BaiduImageBaikeInfoBean baike_info;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	public Boolean getHas_calorie() {
		return has_calorie;
	}
	public void setHas_calorie(Boolean has_calorie) {
		this.has_calorie = has_calorie;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public BaiduImageBaikeInfoBean getBaike_info() {
		return baike_info;
	}
	public void setBaike_info(BaiduImageBaikeInfoBean baike_info) {
		this.baike_info = baike_info;
	}
}
