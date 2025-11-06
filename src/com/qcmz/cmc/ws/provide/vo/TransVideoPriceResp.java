package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransVideoPriceResp extends Response{
	private List<TransVideoPriceBean> result = new ArrayList<TransVideoPriceBean>();

	public List<TransVideoPriceBean> getResult() {
		return result;
	}

	public void setResult(List<TransVideoPriceBean> result) {
		this.result = result;
	}
	
}
