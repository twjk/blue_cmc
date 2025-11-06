package com.qcmz.cmc.ws.provide.vo;

public class CrowdTaskOrderDetailBean extends OrderDetailBean{
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
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 报名人数
	 */
	private Integer applyNum;
	/**
	 * 完成人数
	 */
	private Integer finishNum;
	public CrowdTaskOrderDetailBean() {
		super();
	}
	public CrowdTaskOrderDetailBean(OrderDetailBean orderDetail) {
		super(orderDetail);
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
