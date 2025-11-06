package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcKeyword;
import com.qcmz.cmc.entity.CmcKeywordType;
import com.qcmz.cmc.service.IKeywordService;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 关键词缓存
 */
@Component
public class KeywordMap extends AbstractCacheMap {
	@Autowired
	private IKeywordService keywordService;
	
	/**
	 * <语言，关键词列表>
	 */
	private Map<String, List<CmcKeyword>> map = null;
	/**
	 * <typeId, CmcKeywordType>
	 */
	private Map<Long, CmcKeywordType> typeMap = null;
	
	
	@Override
	protected void init() {
		Map<String, List<CmcKeyword>> temp = new HashMap<String, List<CmcKeyword>>();
		Map<Long, CmcKeywordType> typeMapTemp = new HashMap<Long, CmcKeywordType>();
		
		List<CmcKeyword> list = keywordService.findValidKeyword();
		for (CmcKeyword keyword : list) {
			if(temp.get(keyword.getLangcode())==null){
				temp.put(keyword.getLangcode(), new ArrayList<CmcKeyword>());
			}
			temp.get(keyword.getLangcode()).add(keyword);
		}
		
		List<CmcKeywordType> typeList = keywordService.findKeywordType();
		for (CmcKeywordType type : typeList) {
			typeMapTemp.put(type.getTypeid(), type);
		}
		
		map = temp;
		typeMap = typeMapTemp;
	}

	/**
	 * 匹配关键词
	 * @param str
	 * @param langCode
	 * @return
	 */
	public List<CmcKeyword> matchKeyword(String str, String langCode){
		List<CmcKeyword> result = new ArrayList<CmcKeyword>();
		
		if(StringUtil.isBlankOrNull(str) || !safeInit(map)) return result;
		
		List<CmcKeyword> keywords = map.get(langCode);
		if(keywords==null || keywords.isEmpty()) return result;
		
		for (CmcKeyword keyword : keywords) {
			if(str.indexOf(keyword.getKeyword())>-1){
				result.add(keyword);
			}
		}
		return result;
	}
	
	public CmcKeywordType getType(Long typeId){
		if(safeInit(typeMap)){
			return typeMap.get(typeId);
		}
		return null;
	}
	
	@Override
	public void update(Object obj) {

	}

	@Override
	public void delete(Object obj) {

	}

}
