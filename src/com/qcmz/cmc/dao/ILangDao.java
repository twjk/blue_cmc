package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean;
import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：语言
 * 修改历史：
 */
public interface ILangDao extends IBaseDAO {
	/**
	 * 获取有效语言信息
	 * @param langType
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findLang(String langType, Integer status);
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
	 * 获取语音的语言国际化列表
	 * @return
	 * 修改历史：
	 */
	public List<Lang4SpeechBean> findValidLangInfo4Speech();
}
