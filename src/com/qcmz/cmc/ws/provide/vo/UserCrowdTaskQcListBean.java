package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

public class UserCrowdTaskQcListBean {
	/**
	 * 排序标识
	 */
	private String sortId;
	/**
	 * 审校编号
	 */
	private Long qcId;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 任务图片
	 */
	private String pic;
	/**
	 * 题目数
	 */
	private Integer subjectNum;
	/**
	 * 完成时间
	 */
	private Long finishTime;
	
	/**
	 * 审校状态
	 */
	private Integer qcStatus;
	/**
	 * 审校开始时间
	 */
	private Long qcStartTime;
	/**
	 * 审校完成时间
	 */
	private Long qcFinishTime;
	/**
	 * 已审校题目数
	 */
	private Integer qcFinishNum;
	/**
	 * 审校奖励
	 */
	private Double qcReward;
	/**
	 * 审校奖励计量单位
	 */
	private Integer qcRewardMeasure;
	/**
	 * 奖励类型
	 */
	private Integer rewardType;
	/**
	 * 奖励单位名称
	 */
	private String unitName;
	/**
	 * 审校奖励到账时间
	 */
	private Long qcRewardTime;
	
	public UserCrowdTaskQcListBean() {
		super();
	}

	public UserCrowdTaskQcListBean(String sortId, Long qcId, String utId, String title, String pic, Integer subjectNum, Date finishTime, 
			Integer qcStatus, Date qcStartTime, Date qcFinishTime, Integer qcFinishNum,  
			Double qcReward, Integer qcRewardMeasure, Integer rewardType, String unitName, Date qcRewardTime) {
		super();
		this.sortId = sortId;
		this.qcId = qcId;
		this.utId = utId;
		this.title = title;
		this.pic = pic;
		this.subjectNum = subjectNum;
		this.finishTime = finishTime.getTime();
		this.qcStatus = qcStatus;
		this.qcStartTime = qcStartTime!=null?qcStartTime.getTime():null;
		this.qcFinishTime = qcFinishTime!=null?qcFinishTime.getTime():null;
		this.qcFinishNum = qcFinishNum;
		this.qcReward = qcReward;
		this.qcRewardMeasure = qcRewardMeasure;
		this.rewardType = rewardType;
		this.unitName = unitName;
		this.qcRewardTime = qcRewardTime!=null?qcRewardTime.getTime():null;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public Long getQcId() {
		return qcId;
	}

	public void setQcId(Long qcId) {
		this.qcId = qcId;
	}

	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getSubjectNum() {
		return subjectNum;
	}

	public void setSubjectNum(Integer subjectNum) {
		this.subjectNum = subjectNum;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getQcStatus() {
		return qcStatus;
	}

	public void setQcStatus(Integer qcStatus) {
		this.qcStatus = qcStatus;
	}

	public Long getQcStartTime() {
		return qcStartTime;
	}

	public void setQcStartTime(Long qcStartTime) {
		this.qcStartTime = qcStartTime;
	}

	public Long getQcFinishTime() {
		return qcFinishTime;
	}

	public void setQcFinishTime(Long qcFinishTime) {
		this.qcFinishTime = qcFinishTime;
	}

	public Integer getQcFinishNum() {
		return qcFinishNum;
	}

	public void setQcFinishNum(Integer qcFinishNum) {
		this.qcFinishNum = qcFinishNum;
	}

	public Double getQcReward() {
		return qcReward;
	}

	public void setQcReward(Double qcReward) {
		this.qcReward = qcReward;
	}

	public Integer getQcRewardMeasure() {
		return qcRewardMeasure;
	}

	public void setQcRewardMeasure(Integer qcRewardMeasure) {
		this.qcRewardMeasure = qcRewardMeasure;
	}

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getQcRewardTime() {
		return qcRewardTime;
	}

	public void setQcRewardTime(Long qcRewardTime) {
		this.qcRewardTime = qcRewardTime;
	}
}
