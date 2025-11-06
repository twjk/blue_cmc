package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 5:30:20 PM
 * 修改历史：
 */
public class ApiKeyResponse extends Response {
	/**
	 * ApiKey
	 */
	private String ak;

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}
	
}
