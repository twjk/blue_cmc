package com.qcmz.cmc.ws.provide.vo;

public class LocalTaskUserSubBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 城市编号
	 */
	private Long cityId;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 岗位
	 */
	private String title;
	/**
	 * 工作时间类型
	 */
	private Integer workTimeType;
	/**
	 * 工作多少年
	 */
	private Integer workYear;
	/**
	 * 期望报酬
	 */
	private Integer reward;
	/**
	 * 报酬类型
	 */
	private Integer rewardType;
	/**
	 * 学历
	 */
	private Integer edu;
	/**
	 * 订阅内容
	 */
	private String content;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getWorkTimeType() {
		return workTimeType;
	}
	public void setWorkTimeType(Integer workTimeType) {
		this.workTimeType = workTimeType;
	}
	public Integer getWorkYear() {
		return workYear;
	}
	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}
	public Integer getEdu() {
		return edu;
	}
	public void setEdu(Integer edu) {
		this.edu = edu;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
