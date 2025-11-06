package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeSentenceQueryResp extends Response {
	/**
	 * 语句列表
	 */
	private List<ThemeSentenceBean> sentences = new ArrayList<ThemeSentenceBean>();

	public List<ThemeSentenceBean> getSentences() {
		return sentences;
	}

	public void setSentences(List<ThemeSentenceBean> sentences) {
		this.sentences = sentences;
	}
	
}
