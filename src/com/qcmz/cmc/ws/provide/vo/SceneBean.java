package com.qcmz.cmc.ws.provide.vo;

public class SceneBean {
	/**
	 * 场景编号
	 */
	private Long sceneId;
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 分类名称
	 */
	private String catName;
	/**
	 * 场景名称
	 */
	private String sceneName;
	/**
	 * 图标
	 */
	private String icon;
	
	public SceneBean() {
		super();
	}
	public SceneBean(Long sceneId) {
		super();
		this.sceneId = sceneId;
	}
	public SceneBean(Long sceneId, String transType, String sceneName, String icon) {
		super();
		this.sceneId = sceneId;
		this.transType = transType;
		this.sceneName = sceneName;
		this.icon = icon;
	}
	public SceneBean(Long sceneId, String transType, Long catId, String catName, String sceneName, String icon) {
		super();
		this.sceneId = sceneId;
		this.transType = transType;
		this.catId = catId;
		this.catName = catName;
		this.sceneName = sceneName;
		this.icon = icon;
	}
	
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
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
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
