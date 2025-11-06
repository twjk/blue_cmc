package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class AccountBean {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 点数
	 */
	private int point;
	/**
	 * 广告状态，0关闭1打开
	 */
	private int ad = 1;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getAd() {
		return ad;
	}
	public void setAd(int ad) {
		this.ad = ad;
	}
}
