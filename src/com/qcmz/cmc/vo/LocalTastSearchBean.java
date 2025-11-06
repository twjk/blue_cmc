package com.qcmz.cmc.vo;

import java.util.Date;

public class LocalTastSearchBean {
	/**
	 * 岗位名称
	 */
	private String title;
	/**
	 * 工作时间类型
	 */
	private Integer wrokTimeType;
	/**
	 * 工作城市编号
	 */
	private Long cityId;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 工作多少年
	 */
	private Integer workYear;
	/**
	 * 期望薪酬
	 */
	private Integer reward;
	/**
	 * 薪酬类型
	 */
	private Integer rewardType;
	/**
	 * 学历
	 */
	private Integer edu;
	/**
	 * 大于指定的创建时间
	 */
	private Date createTimeGreaterThan;
	
	private int pageSize;
	private String moreBaseId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getWrokTimeType() {
		return wrokTimeType;
	}
	public void setWrokTimeType(Integer wrokTimeType) {
		this.wrokTimeType = wrokTimeType;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getWorkYear() {
		return workYear;
	}
	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public Integer getEdu() {
		return edu;
	}
	public void setEdu(Integer edu) {
		this.edu = edu;
	}
	public Date getCreateTimeGreaterThan() {
		return createTimeGreaterThan;
	}
	public void setCreateTimeGreaterThan(Date createTimeGreaterThan) {
		this.createTimeGreaterThan = createTimeGreaterThan;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
