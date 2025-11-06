package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicDealBean {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 订单金额
	 */
	private Double amount;
	/**
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 缩略图地址
	 */
	private String thumbUrl;
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 预计翻译时长，秒
	 */
	private Long needTime;
	/**
	 * 翻译列表
	 */
	private List<TransPicTransBean> trans = new ArrayList<TransPicTransBean>();
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public List<TransPicTransBean> getTrans() {
		return trans;
	}
	public void setTrans(List<TransPicTransBean> trans) {
		this.trans = trans;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public Long getNeedTime() {
		return needTime;
	}
	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
	}
}
