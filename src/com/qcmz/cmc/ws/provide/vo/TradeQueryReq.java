package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TradeQueryReq extends Request {
	/**
	 * 查询条件
	 */
	private TradeQueryBean search = new TradeQueryBean();

	public TradeQueryBean getSearch() {
		return search;
	}

	public void setSearch(TradeQueryBean search) {
		this.search = search;
	}
}
