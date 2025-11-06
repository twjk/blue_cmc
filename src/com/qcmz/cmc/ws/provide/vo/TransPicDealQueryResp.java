package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicDealQueryResp extends Response {
	private List<TransPicDealListBean> resultList = new ArrayList<TransPicDealListBean>();

	public List<TransPicDealListBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<TransPicDealListBean> resultList) {
		this.resultList = resultList;
	}
}