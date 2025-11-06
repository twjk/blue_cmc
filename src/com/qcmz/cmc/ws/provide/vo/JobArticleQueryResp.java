package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class JobArticleQueryResp extends Response {
	private List<JobArticleListBean> result = new ArrayList<JobArticleListBean>();

	public List<JobArticleListBean> getResult() {
		return result;
	}

	public void setResult(List<JobArticleListBean> result) {
		this.result = result;
	}
}
