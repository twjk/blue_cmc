package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.WalletInterface;
import com.qcmz.cmc.ws.provide.vo.WalletAccountGetReq;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryReq;
import com.qcmz.cmc.ws.provide.vo.WalletEncourageReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 钱包
 */
public class WalletWSAction extends BaseWSAction {
	@Autowired
	private WalletInterface walletInterface;
	
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
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 激励类型
	 */
	private Integer encourageType;
	
	/**
	 * 获取帐户
	 */
	public String getAccount(){
		WalletAccountGetReq req = new WalletAccountGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
				
		jsonObj = walletInterface.getAccount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取账单
	 */
	public String findBill(){
		WalletBillQueryReq req = new WalletBillQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setIncexp(incexp);
		req.getBean().setBillType(billType);
		req.getBean().setPageSize(pageSize);
		req.getBean().setLastBillId(lastBillId);
				
		jsonObj = walletInterface.findBill(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 激励
	 */
	public String encourage(){
		WalletEncourageReq req = new WalletEncourageReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setEncourageType(encourageType);
		req.getBean().setOrderId(orderId);
				
		jsonObj = walletInterface.encourage(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getEncourageType() {
		return encourageType;
	}

	public void setEncourageType(Integer encourageType) {
		this.encourageType = encourageType;
	}
}
