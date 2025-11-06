package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class JobArticleListBean {
	/**
	 * 资讯编号
	 */
	private Long articleId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 来源名称
	 */
	private String sourceName;
	/**
	 * 链接
	 */
	private String link;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 发布时间
	 */
	private Long publishTime;
	/**
	 * 排序标识
	 */
	private String sortId;
	/**
	 * 最高邀请奖励金额，元
	 */
	private Double maxInviteRewardAmount = 0.0;
	/**
	 * 图片
	 */
	private List<JobArticlePicBean> pics = new ArrayList<JobArticlePicBean>();
	
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public Double getMaxInviteRewardAmount() {
		return maxInviteRewardAmount;
	}
	public void setMaxInviteRewardAmount(Double maxInviteRewardAmount) {
		this.maxInviteRewardAmount = maxInviteRewardAmount;
	}
	public List<JobArticlePicBean> getPics() {
		return pics;
	}
	public void setPics(List<JobArticlePicBean> pics) {
		this.pics = pics;
	}
}
