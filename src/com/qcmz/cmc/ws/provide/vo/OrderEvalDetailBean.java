package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class OrderEvalDetailBean {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 评价编号
	 */
	private Long evalId;
	/**
	 * 评价类型
	 */
	private Integer evalType;
	/**
	 * 评价等级
	 */
	private Integer evalLevel;
	/**
	 * 等级名称
	 */
	private String levelName;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 标签列表
	 */
	private List<OrderEvalTagBean> tags = new ArrayList<OrderEvalTagBean>();
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getEvalId() {
		return evalId;
	}
	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}
	public Integer getEvalType() {
		return evalType;
	}
	public void setEvalType(Integer evalType) {
		this.evalType = evalType;
	}
	public Integer getEvalLevel() {
		return evalLevel;
	}
	public void setEvalLevel(Integer evalLevel) {
		this.evalLevel = evalLevel;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<OrderEvalTagBean> getTags() {
		return tags;
	}
	public void setTags(List<OrderEvalTagBean> tags) {
		this.tags = tags;
	}
}
