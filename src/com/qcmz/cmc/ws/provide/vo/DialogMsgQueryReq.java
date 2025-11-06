package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class DialogMsgQueryReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 更多基准标识
	 */
	private String moreBaseId;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
