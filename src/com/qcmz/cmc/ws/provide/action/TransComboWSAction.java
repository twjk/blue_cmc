package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransComboInterface;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.TransComboAddReq;
import com.qcmz.cmc.ws.provide.vo.TransComboGetReq;
import com.qcmz.cmc.ws.provide.vo.TransComboQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserTransComboExchangeReq;
import com.qcmz.cmc.ws.provide.vo.UserTransComboQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.umc.constant.DictConstant;

public class TransComboWSAction extends BaseWSAction {
	@Autowired
	private TransComboInterface transComboInterface;
	
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
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 套餐编号
	 */
	private Long comboId;
	/**
	 * 套餐包编号
	 */
	private Long pkgId;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 起始时间
	 */
	private Long startTime;
	/**
	 * 总金额
	 */
	private double amount;
	/**
	 * 钱包抵扣金额
	 */
	private double walletAmount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 套餐类型
	 */
	private Integer comboType;
	/**
	 * 场景编号
	 */
	private Long sceneId;
	/**
	 * 兑换码
	 */
	private String exchangeCode;
	
	/**
	 * 获取可用翻译套餐列表
	 * @return
	 */
	public String findCombo(){
		TransComboQueryReq req = new TransComboQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setTransType(transType);
		req.setComboType(comboType);
		req.setSceneId(sceneId);
		
		jsonObj = transComboInterface.findCombo(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取可用翻译套餐
	 * @return
	 */
	public String getCombo(){
		TransComboGetReq req = new TransComboGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);

		req.setComboId(comboId);
		
		jsonObj = transComboInterface.getCombo(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 提交翻译套餐订单
	 * @return
	 */
	public String addComboOrder(){
		TransComboAddReq req = new TransComboAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setUserType(DictConstant.getUserType(userType));
		req.getBean().setEmployeeId(employeeId);
		req.getBean().setEmployeeName(employeeName);
		req.getBean().setComboId(comboId);
		req.getBean().setPkgId(pkgId);
		req.getBean().setNum(num);
		req.getBean().setStartTime(startTime);
		req.getBean().setAmount(amount);
		req.getBean().setWalletAmount(walletAmount);
		req.getBean().setTradeWay(tradeWay);
		
		jsonObj = transComboInterface.addComboOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取翻译套餐订单详细信息
	 * @return
	 */
	public String getComboOrder(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderId(orderId);

		jsonObj = transComboInterface.getComboOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户可用翻译套餐列表
	 * @return
	 */
	public String findUserValidCombo(){
		UserTransComboQueryReq req = new UserTransComboQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTransType(transType);
		req.setComboType(comboType);
		
		jsonObj = transComboInterface.findUserValidCombo(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户翻译套餐列表
	 * @return
	 */
	public String findUserCombo(){
		UserTransComboQueryReq req = new UserTransComboQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTransType(transType);
		req.setComboType(comboType);
		
		jsonObj = transComboInterface.findUserCombo(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 兑换套餐
	 * @return
	 */
	public String exchangeCombo(){
		UserTransComboExchangeReq req = new UserTransComboExchangeReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setExchangeCode(exchangeCode);
		
		jsonObj = transComboInterface.exchangeCombo(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getComboId() {
		return comboId;
	}

	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}

	public Long getPkgId() {
		return pkgId;
	}

	public void setPkgId(Long pkgId) {
		this.pkgId = pkgId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public Long getSceneId() {
		return sceneId;
	}

	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}

	public Integer getComboType() {
		return comboType;
	}

	public void setComboType(Integer comboType) {
		this.comboType = comboType;
	}
}
