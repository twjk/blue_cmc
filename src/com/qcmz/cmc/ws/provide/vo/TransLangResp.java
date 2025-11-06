package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLangResp extends Response{
	private List<TransLangBean> resultList = new ArrayList<TransLangBean>();

	public List<TransLangBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<TransLangBean> resultList) {
		this.resultList = resultList;
	}
}
