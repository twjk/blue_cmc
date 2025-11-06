package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TmGetReq extends Request {
	/**
	 * 翻译机编号
	 */
	private String tmcode;

	public String getTmcode() {
		return tmcode;
	}

	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}
}
