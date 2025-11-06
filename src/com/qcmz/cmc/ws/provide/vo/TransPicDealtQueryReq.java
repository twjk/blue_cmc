package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：人工翻译图片查询
 * 修改历史：
 */
public class TransPicDealtQueryReq extends Request {
	/**
	 * 操作员用户名
	 */
	private String opeator;
	/**
	 * 最后一条图片编号
	 */
	private String lastPicId;
	
	public String getOpeator() {
		return opeator;
	}
	public void setOpeator(String opeator) {
		this.opeator = opeator;
	}
	public String getLastPicId() {
		return lastPicId;
	}
	public void setLastPicId(String lastPicId) {
		this.lastPicId = lastPicId;
	}
}
