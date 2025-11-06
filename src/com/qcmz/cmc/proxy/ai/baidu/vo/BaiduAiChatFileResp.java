package com.qcmz.cmc.proxy.ai.baidu.vo;

import java.util.List;

public class BaiduAiChatFileResp {
	private String id;
	private BaiduAiError error;		//成功返回时无此信息
	private BaiduAiUsage usage;
	private List<BaiduAiChatFileChoice> choices;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BaiduAiError getError() {
		return error;
	}
	public void setError(BaiduAiError error) {
		this.error = error;
	}
	public BaiduAiUsage getUsage() {
		return usage;
	}
	public void setUsage(BaiduAiUsage usage) {
		this.usage = usage;
	}
	public List<BaiduAiChatFileChoice> getChoices() {
		return choices;
	}
	public void setChoices(List<BaiduAiChatFileChoice> choices) {
		this.choices = choices;
	}
}
