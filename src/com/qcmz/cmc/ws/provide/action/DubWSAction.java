package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.DubOrderInterface;
import com.qcmz.cmc.ws.provide.vo.DubAddReq;
import com.qcmz.cmc.ws.provide.vo.DubberQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;

public class DubWSAction extends BaseWSAction {
	@Autowired
	private DubOrderInterface dubInterface;
	
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 作品名称
	 */
	private String title;
	/**
	 * 配音文本
	 */
	private String txt;
	/**
	 * 总金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 用户需求
	 */
	private String requirement;
	/**
	 * 联系手机号
	 */
	private String mobile;
	/**
	 * 配音员编号
	 */
	private Long dubberId;
	/**
	 * 配音员分类编号
	 */
	private Long catId;
	
	/**
	 * 获取机器配音产品信息
	 */
	public String getProduct(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = dubInterface.getProduct(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 创建机器配音订单
	 */
	public String addDub(){
		DubAddReq req = new DubAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTitle(title);
		req.getBean().setTxt(txt);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		
		jsonObj = dubInterface.addDub(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取配音员分类列表
	 */
	public String findDubberCat(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = dubInterface.findDubberCat(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取配音员分类列表
	 */
	public String findDubber(){
		DubberQueryReq req = new DubberQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setCatId(catId);
		
		jsonObj = dubInterface.findDubber(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 创建真人配音订单
	 */
	public String addHumanDub(){
		DubAddReq req = new DubAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTxt(txt);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setMobile(mobile);
		req.getBean().setRequirement(requirement);
		req.getBean().setDubberId(dubberId);
		
		jsonObj = dubInterface.addHumanDub(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 获取配音订单详细信息
	 */
	public String getDub(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = dubInterface.getDub(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
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

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getDubberId() {
		return dubberId;
	}

	public void setDubberId(Long dubberId) {
		this.dubberId = dubberId;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}
}
