package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

public class CrowdTaskOrderAddBean extends OrderCreateBean{
	/**
	 * 任务说明
	 */
	private String content;
	/**
	 * 相关网址
	 */
	private String url;
	/**
	 * 任务单价
	 */
	private double price;
	/**
	 * 参与人数
	 */
	private int peopleNum;
	/**
	 * 任务天数
	 */
	private Integer days;
	/**
	 * 文件
	 */
	private File file;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
