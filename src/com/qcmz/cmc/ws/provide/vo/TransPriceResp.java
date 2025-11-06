package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPriceResp extends Response{
	private List<TransPriceBean> result = new ArrayList<TransPriceBean>();

	public List<TransPriceBean> getResult() {
		return result;
	}

	public void setResult(List<TransPriceBean> result) {
		this.result = result;
	}
	
}
