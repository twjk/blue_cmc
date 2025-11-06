package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：获取翻译图片详细信息请求
 * 修改历史：
 */
public class TransPicGetReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 图片编号
	 */
	private String picId;
	
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
}