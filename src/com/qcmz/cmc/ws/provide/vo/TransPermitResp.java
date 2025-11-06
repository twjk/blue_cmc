package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPermitResp extends Response{
	/**
	 * 许可标识，1许可，0禁止
	 */
	private int permit = 0;

	public int getPermit() {
		return permit;
	}

	public void setPermit(int permit) {
		this.permit = permit;
	}
}
