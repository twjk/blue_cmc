package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;


/**
 * 类说明：翻译图片信息
 * 修改历史：
 */
public class TransPicDealListBean extends OrderDealListBean{
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 翻译途径
	 */
	private String transWay;	
	/**
	 * 图片地址
	 */
	private String picUrl;
	
	public TransPicDealListBean() {
		super();
	}
	
	public TransPicDealListBean(String sortId, String orderId, Integer orderCat, Integer orderType, Long uid, Double amount, Double couponAmount, Long ucid,
			Integer appointFlag, String lon, String lat, String address, String requirement, Date createTime, Date waitTime, Date operTime,
			Long needTime, Date finishTime, String operator, String operatorName, String dealStatus,
			String dealProgress, String checkCd, String checkName, Integer checkStatus, Integer evalStatus, 
			String from, String to, Double commissionAmount, Integer commissionStatus, String platform, String platformVersion,
			String transWay, String picUrl, String thumbUrl) {
		super(sortId, orderId, orderCat, orderType, uid, amount, couponAmount, ucid, appointFlag, lon, lat, address, requirement, createTime, waitTime,
				operTime, needTime, finishTime, operator, operatorName, dealStatus, dealProgress, checkCd, checkName,
				checkStatus, evalStatus, from, to, commissionAmount, commissionStatus, platform, platformVersion);
		this.picId = orderId;
		this.transWay = transWay;
		//列表用缩略图
		this.picUrl = (thumbUrl==null||"".equals(thumbUrl.trim()))?picUrl:thumbUrl;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getTransWay() {
		return transWay;
	}
	public void setTransWay(String transWay) {
		this.transWay = transWay;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}