package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILangDao;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean;
import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：语言
 * 修改历史：
 */
@Repository
public class LangDao extends BaseDAO implements ILangDao {
	/**
	 * 获取有效语言信息
	 * @param langType
	 * @param status
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLang> findLang(String langType, Integer status){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcLang where langtype in (:langtype) ");
		params.put("langtype", DictConstants.findLangType(langType));
		
		if(status!=null){
			sbHql.append(" and status=:status");
			params.put("status", status);
		}
		
		sbHql.append(" order by sortindex, langid");
		
		return (List<CmcLang>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取指定语言代码的语言列表
	 * @param langCodes
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLang> findLangByCode(String langType, List<String> langCodes){
		if(langCodes==null || langCodes.isEmpty()) return new ArrayList<CmcLang>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codes", langCodes);
		params.put("langtype", DictConstants.findLangType(langType));
		
		return (List<CmcLang>) qryListByMap("from CmcLang where  langtype in (:langtype)  and langcode in(:codes) order by sortindex, langid", params);
	}
	
	/**
	 * 获取指定类型语言国际化列表
	 * @param langType not null
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<LangBean> findLangInfo(String langType){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.LangBean(")
				.append("t.langcode, t.cmcLang.langcode3, t.langname, t.language, t.cmcLang.langlc, t.cmcLang.langicon, t.cmcLang.status")
				.append(") from CmcLangIntl t where t.cmcLang.langtype in(:langtype)")
				.append(" order by t.cmcLang.sortindex, t.cmcLang.langid")
		;		
		params.put("langtype", DictConstants.findLangType(langType));
		
		return (List<LangBean>) qryListByMap(sbHql.toString(), params);
	}
	
	
	/**
	 * 获取语音的语言国际化列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<Lang4SpeechBean> findValidLangInfo4Speech(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean(")
				.append("t.langcode, t.cmcLang.langcode3, t.langname, t.language, t.cmcLang.langlc")
				.append(") from CmcLangIntl t where t.cmcLang.langtype in(:langtype)")
				.append(" and t.cmcLang.status=:st")
				.append(" order by t.cmcLang.sortindex, t.cmcLang.langid")
		;
		
		params.put("langtype", DictConstants.findLangType(DictConstants.DICT_LANGTYPE_SPEECH));
		params.put("st", SystemConstants.STATUS_ON);
		
		return (List<Lang4SpeechBean>) qryListByMap(sbHql.toString(), params);
	}
}
