package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransComboQueryReq extends Request {
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 套餐类型
	 */
	private Integer comboType;
	/**
	 * 场景编号
	 */
	private Long sceneId;

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

	public Long getSceneId() {
		return sceneId;
	}

	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
}
