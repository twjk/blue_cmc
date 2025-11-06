package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageCarBean {
	/**
	 * 植物名称
	 */
	private String name;
	/**
	 * 置信度，示例：0.5321
	 */
	private String score;
	/**
	 * 年份
	 */
	private String year;
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public BaiduImageBaikeInfoBean getBaike_info() {
		return baike_info;
	}
	public void setBaike_info(BaiduImageBaikeInfoBean baike_info) {
		this.baike_info = baike_info;
	}
}
