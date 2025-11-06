package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransWordDao;
import com.qcmz.cmc.entity.CmcTransWord;
import com.qcmz.cmc.ws.provide.vo.CountryBean;
import com.qcmz.cmc.ws.provide.vo.TransWordBean;
import com.qcmz.cmc.ws.provide.vo.TransWordQueryBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class TransWordDao extends BaseDAO implements ITransWordDao {
	
	/**
	 * 分页获取词语列表
	 * @param search
	 * @param page
	 * @param pageSize
	 * 修改历史：
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<TransWordBean> findWord(TransWordQueryBean search, int page, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		StringBuilder sbCond = new StringBuilder();
		StringBuilder sbHaving = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransWordBean(word, sum(transcount)) from CmcTransWord");
		
		if(StringUtil.notBlankAndNull(search.getLangCode())){
			sbCond.append(" and langcode=:langcode");
			params.put("langcode", search.getLangCode());
		}
		if(StringUtil.notBlankAndNull(search.getCountryName())){
			sbCond.append(" and country=:country");
			params.put("country", search.getCountryName());
		}		
		
		if(search.getCountFloor()!=null){
			sbHaving.append(" and sum(transcount)>=:floor");
			params.put("floor", search.getCountFloor());
		}
		if(search.getCountCeiling()!=null){
			sbHaving.append(" and sum(transcount)<=:ceiling");
			params.put("ceiling", search.getCountCeiling());
		}
		
		if(sbCond.length()>0){
			sbCond.replace(1, 4, "where");
		}
		sbHql.append(sbCond).append(" group by word");
		
		if(sbHaving.length()>0){
			sbHaving.replace(1, 4, "having");
			sbHql.append(sbHaving);
		}
		
		sbHql.append(" order by sum(transcount) desc");
		
		return (List<TransWordBean>) qryListPage(sbHql.toString(), params, page, pageSize);
	}
	
	/**
	 * 获取词语
	 * @param langCode not null
	 * @param word not null
	 * @param country not null
	 * @return
	 * 修改历史：
	 */
	public CmcTransWord getWord(String langCode, String country, String word){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("langcode", langCode);
		params.put("country", country);
		params.put("word", word);
		
		return (CmcTransWord) load("from CmcTransWord where country=:country and langcode=:langcode and word=:word", params);
	}
	
	/**
	 * 获取国家列表
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CountryBean> findCountry(String langCode){
		StringBuilder sbHql = new StringBuilder("select new com.qcmz.cmc.ws.provide.vo.CountryBean(country) from CmcTransWord");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtil.notBlankAndNull(langCode)){
			sbHql.append(" where langcode=:langcode");
			params.put("langcode", langCode);
		}
		
		sbHql.append(" group by country order by sum(transcount) desc");
		
		return (List<CountryBean>) qryListByMap(sbHql.toString(), params);
	}
}
