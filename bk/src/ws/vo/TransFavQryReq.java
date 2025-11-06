package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Sep 28, 2014 12:15:25 PM
 * 修改历史：
 */
public class TransFavQryReq extends Request{
	/**
	 * 用户标识
	 */
	private String uid;
	/**
	 * 目标语言
	 */
	private String to;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
