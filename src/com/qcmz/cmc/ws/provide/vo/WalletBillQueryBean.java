package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：账单查询
 * 修改历史：
 */
public class WalletBillQueryBean extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 收支类型
	 */
	private Integer incexp;
	/**
	 * 账单类型
	 */
	private Integer billType;
	/**
	 * 每页获取记录数
	 */
	private int pageSize;
	/**
	 * 最后一条账单编号
	 */
	private Long lastBillId;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getLastBillId() {
		return lastBillId;
	}
	public void setLastBillId(Long lastBillId) {
		this.lastBillId = lastBillId;
	}
	public Integer getIncexp() {
		return incexp;
	}
	public void setIncexp(Integer incexp) {
		this.incexp = incexp;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
