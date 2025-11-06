package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class Lang4SpeechResp extends Response {
	private List<Lang4SpeechBean> resultList = new ArrayList<Lang4SpeechBean>();

	public List<Lang4SpeechBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<Lang4SpeechBean> resultList) {
		this.resultList = resultList;
	}
}
