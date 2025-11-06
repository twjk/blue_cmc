package com.qcmz.cmc.ws.provide.vo;

public class RewardAccountBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 余额
	 */
	private double balance;
	/**
	 * 累计奖励
	 */
	private double rewardTotal;
	
	
	public RewardAccountBean() {
		super();
	}
	
	public RewardAccountBean(Long uid) {
		super();
		this.uid = uid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getRewardTotal() {
		return rewardTotal;
	}

	public void setRewardTotal(double rewardTotal) {
		this.rewardTotal = rewardTotal;
	}

}
