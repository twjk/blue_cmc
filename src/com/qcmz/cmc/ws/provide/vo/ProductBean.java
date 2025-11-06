package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductBean {
	/**
	 * 产品名称
	 */
	private String title;
	/**
	 * 服务条款
	 */
	private String content;
	/**
	 * 商品列表
	 */
	private List<ProductItemBean> items = new ArrayList<ProductItemBean>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<ProductItemBean> getItems() {
		return items;
	}
	public void setItems(List<ProductItemBean> items) {
		this.items = items;
	}
}
