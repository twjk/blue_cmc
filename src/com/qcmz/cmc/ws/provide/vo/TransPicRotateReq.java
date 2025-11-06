package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicRotateReq extends Request {
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 顺时针旋转度数
	 */
	private Integer degree;
	
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
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
}
