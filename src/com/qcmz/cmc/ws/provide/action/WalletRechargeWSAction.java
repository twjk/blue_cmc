package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.WalletRechargeInterface;
import com.qcmz.cmc.ws.provide.vo.ApplepaySynReq;
import com.qcmz.cmc.ws.provide.vo.OrderCancelReq;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayReq;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 钱包充值
 */
public class WalletRechargeWSAction extends BaseWSAction {
	@Autowired
	private WalletRechargeInterface walletRechargeInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 充值金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 苹果支付通知
	 */
	private String receipt;
	
	/**
	 * 创建充值订单
	 */
	public String create(){
		WalletRechargeCreateReq req = new WalletRechargeCreateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
	
		req.getBean().setUid(uid);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setOpenid(openid);
				
		jsonObj = walletRechargeInterface.create(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 充值预支付
	 */
	public String prepay(){
		OrderPrepayReq req = new OrderPrepayReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);

		req.getBean().setUid(uid);
		req.getBean().setOrderId(orderId);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setOpenid(openid);
				
		jsonObj = walletRechargeInterface.prepay(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 处理苹果支付通知
	 * @return
	 * 修改历史：
	 */
	public String synApplepay(){
		ApplepaySynReq req = new ApplepaySynReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(orderId);
		req.setReceipt(receipt);
		
		jsonObj = walletRechargeInterface.synApplepay(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消充值
	 */
	public String cancel(){
		OrderCancelReq req = new OrderCancelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setUid(uid);
				
		jsonObj = walletRechargeInterface.cancel(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
}
