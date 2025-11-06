package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：文本翻译详细信息
 * 修改历史：
 */
public class TransTextDealDetailBean extends OrderDealDetailBean{
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 语音
	 */
	private String voice;	
	/**
	 * 上下文
	 */
	private List<TransTextTransBean> contexts = new ArrayList<TransTextTransBean>();
	
	public TransTextDealDetailBean() {
		super();
	}
	public TransTextDealDetailBean(OrderDealDetailBean orderDealBean) {
		super(orderDealBean);
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
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Long waitTime) {
		this.waitTime = waitTime;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
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
	public List<OrderLogBean> getLogs() {
		return logs;
	}
	public void setLogs(List<OrderLogBean> logs) {
		this.logs = logs;
	}
	public List<OrderMsgBean> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<OrderMsgBean> msgs) {
		this.msgs = msgs;
	}
	public List<TransTextTransBean> getContexts() {
		return contexts;
	}
	public void setContexts(List<TransTextTransBean> contexts) {
		this.contexts = contexts;
	}
}