package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.CrowdTaskOrderInterface;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderAddReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.framework.action.BaseWSAction;

public class CrowTaskOrderWSAction extends BaseWSAction {
	@Autowired
	private CrowdTaskOrderInterface crowdTaskOrderInterface;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 总金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 任务说明
	 */
	private String content;
	/**
	 * 相关网址
	 */
	private String url;
	/**
	 * 任务单价
	 */
	private double price;
	/**
	 * 参与人数
	 */
	private int peopleNum;
	/**
	 * 任务天数
	 */
	private Integer days;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 文件
	 */
	private File file;
	
	/**
	 * 最后一条订单编号
	 */
	private String lastOrderId;
	private String pageSize;
	
	/**
	 * 创建众包任务订单
	 */
	public String addCrowdTask(){
		CrowdTaskOrderAddReq req = new CrowdTaskOrderAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderType(orderType);
		req.getBean().setUid(uid);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setContent(content);
		req.getBean().setUrl(url);
		req.getBean().setPrice(price);
		req.getBean().setPeopleNum(peopleNum);
		req.getBean().setDays(days);
		req.getBean().setMobile(mobile);
		req.getBean().setFile(file);
		
		jsonObj = crowdTaskOrderInterface.addCrowdTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取众包任务订单列表
	 */
	public String findCrowdTask(){
		CrowdTaskOrderQueryReq req = new CrowdTaskOrderQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderType(orderType);
		req.setLastOrderId(lastOrderId);
		
		jsonObj = crowdTaskOrderInterface.findCrowdTask(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取众包任务订单详细信息
	 */
	public String getCrowdTask(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = crowdTaskOrderInterface.getCrowdTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getLastOrderId() {
		return lastOrderId;
	}

	public void setLastOrderId(String lastOrderId) {
		this.lastOrderId = lastOrderId;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
