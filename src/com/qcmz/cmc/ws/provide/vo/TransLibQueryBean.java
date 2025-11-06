package com.qcmz.cmc.ws.provide.vo;

public class TransLibQueryBean {
	/**
	 * 最后一条记录编号
	 */
	private Long lastId;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	
	public Long getLastId() {
		return lastId;
	}
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
