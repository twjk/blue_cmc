package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：获取翻译图片详细信息请求
 * 修改历史：
 */
public class TransPicDealGetReq extends Request {
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 图片编号
	 */
	private String picId;
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
}