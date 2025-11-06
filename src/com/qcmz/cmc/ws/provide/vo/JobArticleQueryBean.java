package com.qcmz.cmc.ws.provide.vo;

public class JobArticleQueryBean {
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 更多基准标识
	 */
	private String moreBaseId;

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMoreBaseId() {
		return moreBaseId;
	}

	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
