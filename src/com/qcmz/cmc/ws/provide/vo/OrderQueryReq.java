package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：翻译图片查询
 * 修改历史：
 */
public class OrderQueryReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 员工编号
	 */
	private String employeeId;
	/**
	 * 最后一条订单编号
	 */
	private String lastOrderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getLastOrderId() {
		return lastOrderId;
	}
	public void setLastOrderId(String lastOrderId) {
		this.lastOrderId = lastOrderId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}
