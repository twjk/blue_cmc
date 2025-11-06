package com.qcmz.cmc.ws.provide.vo;

import java.util.List;

/**
 * 类说明：人工文本翻译查询
 * 修改历史：
 */
public class OrderDealQueryBean{
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 处理进度
	 */
	private String dealProgress;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 最后一条记录编号
	 */
	private String moreBaseId;
	/**
	 * 语言列表
	 */
	private List<String> langs;
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getDealProgress() {
		return dealProgress;
	}
	public void setDealProgress(String dealProgress) {
		this.dealProgress = dealProgress;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
	public List<String> getLangs() {
		return langs;
	}
	public void setLangs(List<String> langs) {
		this.langs = langs;
	}
}
