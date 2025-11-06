package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class DaySentenceReq extends Request {
	/**
	 * 编号
	 */
	private Long sentenceId;
	/**
	 * 源语言
	 */
	private String from;

	public Long getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(Long sentenceId) {
		this.sentenceId = sentenceId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
