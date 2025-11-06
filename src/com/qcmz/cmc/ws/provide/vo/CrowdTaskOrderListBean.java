package com.qcmz.cmc.ws.provide.vo;

public class CrowdTaskOrderListBean {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 订单金额，元
	 */
	private Double amount;
	/**
	 * 状态
	 */
	private String dealStatus;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 任务说明
	 */
	private String content;
	/**
	 * 参与人数
	 */
	private Integer peopleNum;
	
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 任务图标
	 */
	private String pic;
	/**
	 * 报名人数
	 */
	private Integer applyNum;
	/**
	 * 完成人数
	 */
	private Integer finishNum;
	
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}
	public Integer getFinishNum() {
		return finishNum;
	}
	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}
}
