package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageRecognitionImageBean {
	/**
	 * 图像编号
	 */
	private Long imageId;
	/**
	 * 图像地址
	 */
	private String imageUrl;
	/**
	 * 图像缩略图地址
	 */
	private String thumbUrl;
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 分类名称
	 */
	private String catName;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 识别结果
	 */
	private List<ImageRecognitionBean> recognitions = new ArrayList<ImageRecognitionBean>();
	
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime.getTime();
	}
	public List<ImageRecognitionBean> getRecognitions() {
		return recognitions;
	}
	public void setRecognitions(List<ImageRecognitionBean> recognitions) {
		this.recognitions = recognitions;
	}
}
