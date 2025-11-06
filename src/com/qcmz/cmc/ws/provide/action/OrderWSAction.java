package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.OrderInterface;
import com.qcmz.cmc.ws.provide.vo.ApplepaySynReq;
import com.qcmz.cmc.ws.provide.vo.OrderCancelReq;
import com.qcmz.cmc.ws.provide.vo.OrderDelReq;
import com.qcmz.cmc.ws.provide.vo.OrderEvalGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderEvalReq;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayReq;
import com.qcmz.cmc.ws.provide.vo.OrderQueryReq;
import com.qcmz.framework.action.BaseWSAction;

public class OrderWSAction extends BaseWSAction {
	@Autowired
	private OrderInterface orderInterface;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 员工编号
	 */
	private String employeeId;
	/**
	 * 支付金额，元
	 */
	private Double amount;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 苹果支付通知
	 */
	private String receipt;
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 最后一条订单编号
	 */
	private String lastOrderId;
	
	/**
	 * 最后一条留言编号
	 */
	private Long lastMsgId;
	/**
	 * 留言内容
	 */
	private String msg;
	/**
	 * 评价编号
	 */
	private Long evalId;
	/**
	 * 评价标签，多个用;分隔
	 */
	private String evalTag;
	
	private String page;
	private String pageSize;
	
	/**
	 * 分页获取用户订单列表
	 */
	public String findOrder(){
		OrderQueryReq req = new OrderQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setEmployeeId(employeeId);
		req.setOrderType(orderType);
		req.setLastOrderId(lastOrderId);
		
		jsonObj = orderInterface.findOrder(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取订单详细信息
	 */
	public String getOrder(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);

		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = orderInterface.getOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 支付预处理
	 */
	public String prePay(){
		OrderPrepayReq req = new OrderPrepayReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setUid(uid);
		req.getBean().setOrderId(orderId);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setOpenid(openid);
		
		jsonObj = orderInterface.prepay(req, interfaceType, getRemoteAddr());
		
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
		
		jsonObj = orderInterface.synApplepay(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消订单
	 */
	public String cancelOrder(){
		OrderCancelReq req = new OrderCancelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(orderId);
		req.setReason(reason);
		
		jsonObj = orderInterface.cancelOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 删除订单
	 */
	public String delOrder(){
		OrderDelReq req = new OrderDelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = orderInterface.delOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 以订单为单位分页获取用户最新留言列表
	 * @return
	 */
	public String findRecentMsg(){
		OrderMsgQueryReq req = new OrderMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(orderId);
		req.setLastMsgId(lastMsgId);
		
		jsonObj = orderInterface.findRecentMsg(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户历史留言列表
	 */
	public String findMsg(){
		OrderMsgQueryReq req = new OrderMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(orderId);
		req.setLastMsgId(lastMsgId);
		
		jsonObj = orderInterface.findMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户新的留言列表
	 */
	public String findLastMsg(){
		OrderMsgQueryReq req = new OrderMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(orderId);
		req.setLastMsgId(lastMsgId);
		
		jsonObj = orderInterface.findLastMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 添加留言
	 */
	public String addMsg(){
		OrderMsgAddReq req = new OrderMsgAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setOrderId(orderId);
		req.getBean().setMsg(msg);
		
		jsonObj = orderInterface.addMsg(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 评价订单
	 */
	public String evalOrder(){
		OrderEvalReq req = new OrderEvalReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setOrderId(orderId);
		req.getBean().setEvalId(evalId);
		req.getBean().setEvalTag(evalTag);		
		
		jsonObj = orderInterface.evalOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取订单评价信息
	 */
	public String getOrderEval(){
		OrderEvalGetReq req = new OrderEvalGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = orderInterface.getOrderEval(req, interfaceType, getRemoteAddr());
		
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getLastMsgId() {
		return lastMsgId;
	}

	public void setLastMsgId(Long lastMsgId) {
		this.lastMsgId = lastMsgId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getLastOrderId() {
		return lastOrderId;
	}

	public void setLastOrderId(String lastOrderId) {
		this.lastOrderId = lastOrderId;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getEvalId() {
		return evalId;
	}

	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}

	public String getEvalTag() {
		return evalTag;
	}

	public void setEvalTag(String evalTag) {
		this.evalTag = evalTag;
	}
}
