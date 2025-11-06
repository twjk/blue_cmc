package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：翻译图片查询
 * 修改历史：
 */
public class TransPicQueryReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 最后一条图片编号
	 */
	private String lastPicId;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getLastPicId() {
		return lastPicId;
	}
	public void setLastPicId(String lastPicId) {
		this.lastPicId = lastPicId;
	}
}
