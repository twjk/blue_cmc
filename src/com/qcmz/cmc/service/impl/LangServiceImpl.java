package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IFuncCapDao;
import com.qcmz.cmc.dao.ILangDao;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.service.ILangService;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean;
import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.framework.constant.SystemConstants;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class LangServiceImpl implements ILangService {
	@Autowired
	private ILangDao langDao;
	@Autowired
	private IFuncCapDao funcCapDao;
	
	/**
	 * 获取有效语言信息
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findValid(String langType){
		return langDao.findLang(langType, SystemConstants.STATUS_ON);
	}
	
	/**
	 * 获取指定语言代码的语言列表
	 * @param langCodes
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findLangByCode(String langType, List<String> langCodes){
		return langDao.findLangByCode(langType, langCodes);
	}
	
	/**
	 * 获取指定类型语言国际化列表
	 * @param langType not null
	 * @return
	 * 修改历史：
	 */
	public List<LangBean> findLangInfo(String langType){
		return langDao.findLangInfo(langType);
	}
	
	/**
	 * 获取语音的语言信息列表
	 * @return
	 * 修改历史：
	 */
	public List<Lang4SpeechBean> findValidLangInfo4Speech(){
		//语言能力
		List<String> asrLangs = funcCapDao.findAsrLang();
		List<String> ttsLangs = funcCapDao.findTtsLang();
		List<String> ocrLangs = funcCapDao.findOcrLang();
		
		List<Lang4SpeechBean> result = langDao.findValidLangInfo4Speech();
		for (Lang4SpeechBean bean : result) {
			bean.setAsr(asrLangs.contains(bean.getLangCode()));
			bean.setTts(ttsLangs.contains(bean.getLangCode()));
			bean.setOcr(ocrLangs.contains(bean.getLangCode()));
		}
		return result; 
	}
}
