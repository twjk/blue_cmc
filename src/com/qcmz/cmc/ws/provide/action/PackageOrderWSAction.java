package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.PackageOrderInterface;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.PackageOrderAddReq;
import com.qcmz.cmc.ws.provide.vo.PackageOrderExchangedReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.umc.constant.DictConstant;

public class PackageOrderWSAction extends BaseWSAction {
	@Autowired
	private PackageOrderInterface packageOrderInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 员工编号
	 */
	private String employeeId;
	/**
	 * 员工姓名
	 */
	private String employeeName;
	/**
	 * 活动编号
	 */
	private Long actId;
	/**
	 * 钱包抵扣金额
	 */
	private double walletAmount;
	/**
	 * 总金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 兑换码
	 */
	private String exchangeCode;
	/**
	 * 兑换时间
	 */
	private Long exchangeTime;
	/**
	 * 起始时间
	 */
	private Long startTime;
	
	/**
	 * 创建优惠券包订单
	 * @return
	 */
	public String addPackage(){
		PackageOrderAddReq req = new PackageOrderAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setUserType(DictConstant.getUserType(userType));
		req.getBean().setEmployeeId(employeeId);
		req.getBean().setEmployeeName(employeeName);
		req.getBean().setActId(actId);
		req.getBean().setWalletAmount(walletAmount);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setStartTime(startTime);
		
		jsonObj = packageOrderInterface.addPackage(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取优惠券包订单详细信息
	 * @return
	 */
	public String getPackage(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = packageOrderInterface.getPackage(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	
	/**
	 * 保存优惠券包兑换信息
	 * @return
	 */
	public String saveExchanged(){
		PackageOrderExchangedReq req = new PackageOrderExchangedReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setOrderId(orderId);
		req.getBean().setExchangeCode(exchangeCode);
		req.getBean().setExchangeTime(exchangeTime);
		
		jsonObj = packageOrderInterface.saveExchanged(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public double getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
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
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public Long getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(Long exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
}
