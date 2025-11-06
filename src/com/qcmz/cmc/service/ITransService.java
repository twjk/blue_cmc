package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.ws.provide.vo.TransLangBean;

/**
 * 类说明：翻译服务
 * 修改历史：
 */
public interface ITransService {
	/**
	 * 获取翻译语言列表
	 * @return
	 * 修改历史：
	 */
	public List<TransLangBean> findLang();
	/**
	 * 收藏翻译结果
	 * @param uid 用户标识
	 * @param from 源语言
	 * @param src 原文
	 * @param to 目标语言
	 * @param dst 译文
	 * 修改历史：
	 */
	public void favTrans(String uid, String from, String src, String to, String dst);
}
