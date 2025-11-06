package com.qcmz.cmc.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ArticleChannelHtmlBean {
	private String title;
	private String catId;
	private List<ArticleListHtmlBean> list = new ArrayList<ArticleListHtmlBean>();
	private List<PageHtmlBean> pageList = new ArrayList<PageHtmlBean>();
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ArticleListHtmlBean> getList() {
		return list;
	}
	public void setList(List<ArticleListHtmlBean> list) {
		this.list = list;
	}
	public List<PageHtmlBean> getPageList() {
		return pageList;
	}
	public void setPageList(List<PageHtmlBean> pageList) {
		this.pageList = pageList;
	}
}
