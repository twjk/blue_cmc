package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

/**
 * 类说明：短文快译信息
 * 修改历史：
 */
public class TransTextDealListBean extends OrderDealListBean{
	
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	
	public TransTextDealListBean() {
		super();
	}
	public TransTextDealListBean(String sortId, String orderId, Integer orderCat, Integer orderType, Long uid, Double amount, Double couponAmount, Long ucid,
			Integer appointFlag, String lon, String lat, String address, String requirement, Date createTime, Date waitTime, Date operTime,
			Long needTime, Date finishTime, String operator, String operatorName, String dealStatus,
			String dealProgress, String checkCd, String checkName, Integer checkStatus, Integer evalStatus, 
			String from, String to, Double commissionAmount, Integer commissionStatus, String platform, String platformVersion, 
			String src, String dst) {
		super(sortId, orderId, orderCat, orderType, uid, amount, couponAmount, ucid, appointFlag, lon, lat, address, requirement, createTime, waitTime,
				operTime, needTime, finishTime, operator, operatorName, dealStatus, dealProgress, checkCd, checkName,
				checkStatus, evalStatus, from, to, commissionAmount, commissionStatus, platform, platformVersion);
		this.src = src;
		this.dst = dst;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
}