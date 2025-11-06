package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class OrderMsgBean {
	/**
	 * 留言编号
	 */
	private Long msgId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 图片编号（保留兼容老版本）
	 */
	@Deprecated
	private String picId;
	/**
	 * 内容
	 */
	private String msg;
	/**
	 * 留言方
	 */
	private String sideType;
	/**
	 * 留言时间
	 */
	private Long createTime;
	
	public OrderMsgBean() {
		super();
	}
	public OrderMsgBean(Long msgId, String orderId, Integer orderTpe, String msg, String sideType, Date createTime) {
		super();
		this.msgId = msgId;
		this.orderId = orderId;
		this.picId = orderId;
		this.orderType = orderTpe;
		this.msg = msg;
		this.sideType = sideType;
		this.createTime = createTime.getTime();
	}

	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getSideType() {
		return sideType;
	}
	public void setSideType(String sideType) {
		this.sideType = sideType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
