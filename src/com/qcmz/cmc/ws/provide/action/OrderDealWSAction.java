package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.OrderDealInterface;
import com.qcmz.cmc.ws.provide.vo.OrderAcceptReq;
import com.qcmz.cmc.ws.provide.vo.OrderAppointTimeUpdateReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderHandoverReq;
import com.qcmz.cmc.ws.provide.vo.OrderPickReq;
import com.qcmz.cmc.ws.provide.vo.OrderRejectReq;
import com.qcmz.cmc.ws.provide.vo.OrderStartCheckReq;
import com.qcmz.framework.action.BaseWSAction;

public class OrderDealWSAction extends BaseWSAction {
	@Autowired
	private OrderDealInterface orderDealInterface;

	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	/**
	 * 最后一条留言编号
	 */
	private Long lastMsgId;
	/**
	 * 留言内容
	 */
	private String msg;
	/**
	 * 需要多长时间，秒
	 */
	private Integer needTime;
	/**
	 * 预约时间
	 */
	private Long appointTime;
	
	/**
	 * 捡单
	 * @return
	 * 修改历史：
	 */
	public String pickOrder(){
		OrderPickReq req = new OrderPickReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.pickOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 修改预约时间
	 * @return
	 * 修改历史：
	 */
	public String modAppointTime(){
		OrderAppointTimeUpdateReq req = new OrderAppointTimeUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setAppointTime(appointTime);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.modAppointTime(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 接单
	 * @return
	 * 修改历史：
	 */
	public String acceptOrder(){
		OrderAcceptReq req = new OrderAcceptReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.acceptOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 交单
	 * @return
	 * 修改历史：
	 */
	public String handoverOrder(){
		OrderHandoverReq req = new OrderHandoverReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setFrom(from);
		req.setTo(to);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.handoverOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 拒单
	 * @return
	 * 修改历史：
	 */
	public String rejectOrder(){
		OrderRejectReq req = new OrderRejectReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setReason(reason);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.rejectOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 开始校对
	 * @return
	 * 修改历史：
	 */
	public String startCheck(){
		OrderStartCheckReq req = new OrderStartCheckReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.startCheck(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 添加客服留言
	 * @return
	 * 修改历史：
	 */
	public String addMsg(){
		OrderDealMsgAddReq req = new OrderDealMsgAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setMsg(msg);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.addMsg(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取留言列表
	 * @return
	 * 修改历史：
	 */
	public String findMsg(){
		OrderDealMsgQueryReq req = new OrderDealMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		req.setLastMsgId(lastMsgId);
		req.setOperator(operator);
		
		jsonObj = orderDealInterface.findMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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

	public Integer getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Integer needTime) {
		this.needTime = needTime;
	}

	public Long getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Long appointTime) {
		this.appointTime = appointTime;
	}
}
