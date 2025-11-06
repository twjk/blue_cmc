package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Sep 4, 2014 3:31:44 PM
 * 修改历史：
 */
public class TransPermitReq extends Request{
	/**
	 * 用户标识
	 */
	private String token;
	/**
	 * 语言
	 */
	private String lang;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
