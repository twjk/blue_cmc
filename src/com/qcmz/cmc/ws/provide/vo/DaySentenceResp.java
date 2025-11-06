package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class DaySentenceResp extends Response {
	/**
	 * 每日一句信息
	 */
	private DaySentenceBean bean;

	public DaySentenceBean getBean() {
		return bean;
	}

	public void setBean(DaySentenceBean bean) {
		this.bean = bean;
	}
	
}
