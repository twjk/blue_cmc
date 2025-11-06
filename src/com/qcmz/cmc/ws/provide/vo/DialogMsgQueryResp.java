package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class DialogMsgQueryResp extends Response {
	private List<DialogMsgBean> resultList = new ArrayList<DialogMsgBean>();

	public List<DialogMsgBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<DialogMsgBean> resultList) {
		this.resultList = resultList;
	}
	
}
