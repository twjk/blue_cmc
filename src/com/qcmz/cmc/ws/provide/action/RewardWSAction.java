package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.RewardInterface;
import com.qcmz.cmc.ws.provide.vo.AlipayGetOpenidReq;
import com.qcmz.cmc.ws.provide.vo.RewardAccountGetReq;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryReq;
import com.qcmz.cmc.ws.provide.vo.RewardCashApplyReq;
import com.qcmz.cmc.ws.provide.vo.RewardInviteReq;
import com.qcmz.cmc.ws.provide.vo.RewardRewardReq;
import com.qcmz.framework.action.BaseWSAction;

public class RewardWSAction extends BaseWSAction {
	@Autowired
	private RewardInterface rewardInterface;
	
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
	 * 提现金额
	 */
	private Double amount;
	/**
	 * 帐户类型
	 */
	private String accountType;
	/**
	 * 帐户
	 */
	private String account;
	
	/**
	 * 支付宝授权码
	 */
	private String alipayAuthCode;
	/**
	 * 奖励金额
	 */
	private Double rewardAmount;
	/**
	 * 子业务类型
	 */
	private String subServiceType;
	/**
	 * 邀请人用户编号
	 */
	private Long inviterId;
	/**
	 * 被邀请人用户编号
	 */
	private Long inviteeId;
	/**
	 * 业务编号
	 */
	private String serviceId;
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 每页获取记录数
	 */
	private int pageSize;
	/**
	 * 最后一条账单编号
	 */
	private Long lastBillId;

	
	/**
	 * 获取帐户信息
	 */
	public String getAccount(){
		RewardAccountGetReq req = new RewardAccountGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
				
		jsonObj = rewardInterface.getAccount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页查询账单列表
	 */
	public String findBill(){
		RewardBillQueryReq req = new RewardBillQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setPageSize(pageSize);
		req.getBean().setIncexp(incexp);
		req.getBean().setBillType(billType);
		req.getBean().setLastBillId(lastBillId);
				
		jsonObj = rewardInterface.findBill(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 奖励
	 */
	public String reward(){
		RewardRewardReq req = new RewardRewardReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setRewardAmount(rewardAmount);
		req.getBean().setSubServiceType(subServiceType);
		req.getBean().setServiceId(serviceId);
		req.getBean().setDescription(description);
			
		jsonObj = rewardInterface.reward(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 邀请奖励
	 */
	public String inviteReward(){
		RewardInviteReq req = new RewardInviteReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setInviterId(inviterId);
		req.getBean().setInviteeId(inviteeId);
		req.getBean().setSubServiceType(subServiceType);
		req.getBean().setServiceId(serviceId);
			
		jsonObj = rewardInterface.inviteReward(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 申请提现
	 */
	public String applyCash(){
		RewardCashApplyReq req = new RewardCashApplyReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setAmount(amount);
		req.getBean().setAccountType(accountType);
		req.getBean().setAccount(account);
			
		jsonObj = rewardInterface.applyCash(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 获取支付宝openid
	 */
	public String getAlipayOpenid(){
		AlipayGetOpenidReq req = new AlipayGetOpenidReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setAlipayAuthCode(alipayAuthCode);
		
		jsonObj = rewardInterface.getAlipayOpenid(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getLastBillId() {
		return lastBillId;
	}

	public void setLastBillId(Long lastBillId) {
		this.lastBillId = lastBillId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAlipayAuthCode() {
		return alipayAuthCode;
	}

	public void setAlipayAuthCode(String alipayAuthCode) {
		this.alipayAuthCode = alipayAuthCode;
	}

	public Double getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Double rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public String getSubServiceType() {
		return subServiceType;
	}

	public void setSubServiceType(String subServiceType) {
		this.subServiceType = subServiceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RewardInterface getRewardInterface() {
		return rewardInterface;
	}

	public void setRewardInterface(RewardInterface rewardInterface) {
		this.rewardInterface = rewardInterface;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public Long getInviteeId() {
		return inviteeId;
	}

	public void setInviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
