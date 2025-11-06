package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserTransComboQueryReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 适用翻译类型
	 */
	private String transType;
	/**
	 * 套餐类型
	 */
	private Integer comboType;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getComboType() {
		return comboType;
	}

	public void setComboType(Integer comboType) {
		this.comboType = comboType;
	}
}
