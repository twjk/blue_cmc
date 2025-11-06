package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class SceneQueryResp extends Response {
	private List<SceneBean> result = new ArrayList<SceneBean>();

	public List<SceneBean> getResult() {
		return result;
	}

	public void setResult(List<SceneBean> result) {
		this.result = result;
	}
}
