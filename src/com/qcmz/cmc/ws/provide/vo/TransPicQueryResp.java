package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicQueryResp extends Response {
	private List<TransPicBean> resultList = new ArrayList<TransPicBean>();

	public List<TransPicBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<TransPicBean> resultList) {
		this.resultList = resultList;
	}
}