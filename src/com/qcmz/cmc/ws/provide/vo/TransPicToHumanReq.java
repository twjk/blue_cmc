package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：转人工翻译请求
 * 修改历史：
 */
public class TransPicToHumanReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 用户需求
	 */
	private String requirement;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
}
