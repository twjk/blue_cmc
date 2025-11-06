package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeResp extends Response {
	/**
	 * 详细信息
	 */
	private ThemeDetailBean detail;

	public ThemeDetailBean getDetail() {
		return detail;
	}

	public void setDetail(ThemeDetailBean detail) {
		this.detail = detail;
	}
	
}
