package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class UserCrowdTaskApplyBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 用户设备标识
	 */
	private String uuid;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 前置任务答题
	 */
	private List<UserCrowdAnswerBean> preAnswers = new ArrayList<UserCrowdAnswerBean>();
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public List<UserCrowdAnswerBean> getPreAnswers() {
		return preAnswers;
	}
	public void setPreAnswers(List<UserCrowdAnswerBean> preAnswers) {
		this.preAnswers = preAnswers;
	}
}
