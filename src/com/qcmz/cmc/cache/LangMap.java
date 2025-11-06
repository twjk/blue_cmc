package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.ILangService;
import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class LangMap extends AbstractCacheMap {
	@Autowired
	private ILangService langService;
	
	/**
	 * <langType|langCode|language, LangBean>
	 */
	private Map<String, LangBean> map = null;
	/**
	 * <langType, List<LangBean>>
	 */
	private Map<String, List<LangBean>> langMap = null;
	/**
	 * <langType|language, List<LangBean>>
	 */
	private Map<String, List<LangBean>> langMap2 = null;
	/**
	 * <language, List<LangBean>>
	 */
	private Map<String, List<Lang4SpeechBean>> speechMap = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		Map<String, LangBean> mapTemp = new HashMap<String, LangBean>();
		Map<String, List<LangBean>> langMapTemp = new HashMap<String, List<LangBean>>();
		Map<String, List<LangBean>> langMap2Temp = new HashMap<String, List<LangBean>>();
		Map<String, List<Lang4SpeechBean>> speechMapTemp = new HashMap<String, List<Lang4SpeechBean>>();
		String key = null;
		List<LangBean> baseList;
		
		for (String langType : DictConstants.DICT_LANGTYPES) {
			baseList = langService.findLangInfo(langType);
			for (LangBean langBean : baseList) {
				key = getKey4Map(langType, langBean.getLangCode(), langBean.getLanguage());
				mapTemp.put(key, langBean);
				
				key = getKey4LangMap2(langType, langBean.getLanguage());
				if(langMap2Temp.get(key)==null){
					langMap2Temp.put(key, new ArrayList<LangBean>());
				}
				langMap2Temp.get(key).add(langBean);
				
				if(langMapTemp.get(langType)==null){
					langMapTemp.put(langType, new ArrayList<LangBean>());
				}
				langMapTemp.get(langType).add(langBean);
			}
		}
		
		map = mapTemp;
		langMap = langMapTemp;
		langMap2 = langMap2Temp;
		
		//语音类语言，含能力
		List<Lang4SpeechBean> list = langService.findValidLangInfo4Speech();
		for (Lang4SpeechBean langBean : list) {
			if(speechMapTemp.get(langBean.getLanguage())==null){
				speechMapTemp.put(langBean.getLanguage(), new ArrayList<Lang4SpeechBean>());
			}
			speechMapTemp.get(langBean.getLanguage()).add(langBean);
		}
		
		speechMap = speechMapTemp;
	}

	private String getKey4Map(String langType, String langCode, String language){
		return new StringBuilder(langType).append("|").append(langCode).append("|").append(language).toString();
	}
	
	private String getKey4LangMap2(String langType, String language){
		return new StringBuilder(langType).append("|").append(language).toString();
	}
	
	/**
	 * 获取语言列表
	 * @param langType
	 * @param language
	 * @return
	 * 修改历史：
	 */
	public List<LangBean> findLang(String langType, String language){
		if(StringUtil.notBlankAndNull(language)){
			return langMap2.get(getKey4LangMap2(langType, language));
		}else{
			return langMap.get(langType);
		}
	}
	
	/**
	 * 获取语音的语言中文信息
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public LangBean getLangInfo4Speech(String langCode){
		LangBean result = map.get(getKey4Map(DictConstants.DICT_LANGTYPE_SPEECH, langCode, DictConstants.DICT_LANG_ZHCN));
		if(result==null){
			result = map.get(getKey4Map(DictConstants.DICT_LANGTYPE_OTHER, langCode, DictConstants.DICT_LANG_ZHCN));
		}
		return result;
	}
	
	/**
	 * 获取语言中文信息
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public LangBean getLangInfo4Text(String langCode){
		LangBean result = map.get(getKey4Map(DictConstants.DICT_LANGTYPE_TEXT, langCode, DictConstants.DICT_LANG_ZHCN));
		if(result==null){
			result = map.get(getKey4Map(DictConstants.DICT_LANGTYPE_OTHER, langCode, DictConstants.DICT_LANG_ZHCN));
		}
		return result;
	}
	
	/**
	 * 获取语言信息
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public LangBean getLangInfo4Text(String langCode, String language){
		return map.get(getKey4Map(DictConstants.DICT_LANGTYPE_TEXT, langCode, language));
	}
	
	/**
	 * 获取语音的语言中文名称
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public String getLangName4Speech(String langCode){
		LangBean lang = getLangInfo4Speech(langCode);
		if(lang!=null){
			return lang.getLangName();
		}
		return null;
	}
	
	/**
	 * 获取文字的语言中文名称
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public String getLangName4Text(String langCode){
		LangBean lang = getLangInfo4Text(langCode);
		if(lang!=null){
			return lang.getLangName();
		}
		return null;
	}
	
	/**
	 * 获取文字的语言中文名称
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public String getLangName4Text(String langCode, String language){
		LangBean lang = getLangInfo4Text(langCode, language);
		if(lang!=null){
			return lang.getLangName();
		}
		return null;
	}
	
	/**
	 * 获取语音的语言列表
	 * @param language 空则返回所有
	 * @return
	 * 修改历史：
	 */
	public List<Lang4SpeechBean> findLang4Speech(String language){
		List<Lang4SpeechBean> result = null;
		if(StringUtil.notBlankAndNull(language)){
			result = speechMap.get(language);
			if(result == null){
				result = speechMap.get(DictConstants.DICT_LANG_EN);
			}
			if(result == null){
				result = new ArrayList<Lang4SpeechBean>();
			}
		}else{
			result = new ArrayList<Lang4SpeechBean>();
			for (String lang : speechMap.keySet()) {
				result.addAll(speechMap.get(lang));
			}
		}
		return result;
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
	}

	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}

}
