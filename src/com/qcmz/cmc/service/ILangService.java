package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ILangService {
	/**
	 * 获取有效语言信息
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findValid(String langType);
	/**
	 * 获取指定语言代码的语言列表
	 * @param langCodes
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findLangByCode(String langType, List<String> langCodes);
	/**
	 * 获取指定类型语言国际化列表
	 * @param langType not null
	 * @return
	 * 修改历史：
	 */
	public List<LangBean> findLangInfo(String langType);
	/**
	 * 获取语言信息列表
	 * @param language
	 * @return
	 * 修改历史：
	 */
	public List<Lang4SpeechBean> findValidLangInfo4Speech();
}
