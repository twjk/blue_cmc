package com.qcmz.cmc.proxy.spider.weixin.vo;

import java.util.List;

public class OfficialAccountGetResp extends WeixinResp{
	private List<OfficialAccountBean> list;

	public List<OfficialAccountBean> getList() {
		return list;
	}

	public void setList(List<OfficialAccountBean> list) {
		this.list = list;
	}
}
