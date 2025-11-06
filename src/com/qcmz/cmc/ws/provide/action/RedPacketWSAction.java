package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.RedPacketInterface;
import com.qcmz.cmc.ws.provide.vo.RedPacketAccountGetReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketCancelReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketCashApplyReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketGetReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketPrepayReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceiveReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceivedQueryReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketSentQueryReq;
import com.qcmz.framework.action.BaseWSAction;

public class RedPacketWSAction extends BaseWSAction {
	@Autowired
	private RedPacketInterface redPacketInterface;
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 发红包用户编号
	 */
	private Long uid;
	/**
	 * 领取红包用户编号
	 */
	private Long receiverId;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 红包暗语
	 */
	private String title;
	/**
	 * 中文红包暗语
	 */
	private String titleCn;
	/**
	 * 红包数量
	 */
	private int packetNum;
	/**
	 * 支付金额
	 */
	private double amount;
	/**
	 * 应付金额
	 */
	private double payableAmount;
	/**
	 * 红包金额
	 */
	private double packetAmount;
	/**
	 * 服务费
	 */
	private double serviceAmount;
	/**
	 * 余额抵扣金额
	 */
	private double deductAmount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	
	/**
	 * 文件
	 */
	private File file;
	private String page;
	private String pageSize;
	
	/**
	 * 获取帐户信息
	 */
	public String getAccount(){
		RedPacketAccountGetReq req = new RedPacketAccountGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
				
		jsonObj = redPacketInterface.getAccount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 创建红包
	 */
	public String create(){
		RedPacketCreateReq req = new RedPacketCreateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setLangCode(langCode);
		req.getBean().setTitle(title);
		req.getBean().setTitleCn(titleCn);
		req.getBean().setPacketNum(packetNum);
		req.getBean().setPacketAmount(packetAmount);
		req.getBean().setServiceAmount(serviceAmount);
		req.getBean().setPayableAmount(payableAmount);
		req.getBean().setDeductAmount(deductAmount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setOpenid(openid);
				
		jsonObj = redPacketInterface.create(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消
	 */
	public String cancel(){
		RedPacketCancelReq req = new RedPacketCancelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setPacketId(packetId);
		req.setUid(uid);
				
		jsonObj = redPacketInterface.cancel(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	
	/**
	 * 预支付
	 */
	public String prepay(){
		RedPacketPrepayReq req = new RedPacketPrepayReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setPacketId(packetId);
		req.getBean().setUid(uid);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setOpenid(openid);
				
		jsonObj = redPacketInterface.prepay(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取红包信息
	 */
	public String getPacket(){
		RedPacketGetReq req = new RedPacketGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setPacketId(packetId);
		req.setReceiverId(receiverId);
				
		jsonObj = redPacketInterface.getPacket(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 领红包
	 */
	public String receive(){
		RedPacketReceiveReq req = new RedPacketReceiveReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setPacketId(packetId);
		req.setReceiverId(receiverId);
		req.setFile(file);
			
		jsonObj = redPacketInterface.receive(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 申请提现
	 */
	public String applyCash(){
		RedPacketCashApplyReq req = new RedPacketCashApplyReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setAmount(amount);
		req.setOpenid(openid);
			
		jsonObj = redPacketInterface.applyCash(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户发出的红包列表
	 */
	public String findUserSent(){
		RedPacketSentQueryReq req = new RedPacketSentQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
				
		jsonObj = redPacketInterface.findUserSent(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户领取的红包列表
	 */
	public String findUserReceived(){
		RedPacketReceivedQueryReq req = new RedPacketReceivedQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setReceiverId(receiverId);
				
		jsonObj = redPacketInterface.findUserReceived(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public String getPacketId() {
		return packetId;
	}

	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleCn() {
		return titleCn;
	}

	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}

	public int getPacketNum() {
		return packetNum;
	}

	public void setPacketNum(int packetNum) {
		this.packetNum = packetNum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public double getPacketAmount() {
		return packetAmount;
	}

	public void setPacketAmount(double packetAmount) {
		this.packetAmount = packetAmount;
	}

	public double getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public double getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(double deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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
}
