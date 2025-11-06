package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class ImageRecognitionQueryReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 每页记录数
	 */
	private Integer pageSize;
	/**
	 * 上一页最后一条记录编号
	 */
	private Long lastId;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getLastId() {
		return lastId;
	}
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
}
