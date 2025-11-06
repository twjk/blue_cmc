package com.qcmz.cmc.proxy.spider.weixin.vo;

import java.util.List;

public class ArticleListResp extends WeixinResp{
	//app_msg_list
    private List<ArticleListBean> appMsgList;
    //总数，app_msg_cnt
    private Integer appMsgCnt;
	public List<ArticleListBean> getAppMsgList() {
		return appMsgList;
	}
	public void setAppMsgList(List<ArticleListBean> appMsgList) {
		this.appMsgList = appMsgList;
	}
	public Integer getAppMsgCnt() {
		return appMsgCnt;
	}
	public void setAppMsgCnt(Integer appMsgCnt) {
		this.appMsgCnt = appMsgCnt;
	}
}
