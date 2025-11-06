package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class DubberQueryReq extends Request {
	/**
	 * 配音员分类编号
	 */
	private Long catId;

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}
}
