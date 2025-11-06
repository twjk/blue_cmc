package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cms.ws.provide.locator.ArticleWS;
import com.qcmz.cms.ws.provide.vo.ArticleTagLangBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 资讯标签缓存
 */
@Component
public class ArticleTagMap extends AbstractCacheMap {
	/**
	 * 标签<语言，标签列表>
	 */
	private Map<String, List<String>> map = null;
	
	@Override
	protected void init() {
		Map<String, List<String>> temp = new HashMap<String, List<String>>();
		List<ArticleTagLangBean> list = ArticleWS.findTagLang();
		if(list!=null){
			for (ArticleTagLangBean bean : list) {
				if(temp.get(bean.getLangCode())==null){
					temp.put(bean.getLangCode(), new ArrayList<String>());
				}
				temp.get(bean.getLangCode()).add(bean.getTag());
			}
			map = temp;
		}
	}

	/**
	 * 匹配标签
	 * @param str
	 * @return
	 */
	public List<String> matchTag(String str, String langCode){
		List<String> result = new ArrayList<String>();
		
		if(StringUtil.isBlankOrNull(str) || !safeInit(map)) return result;
		
		List<String> tags = map.get(langCode);
		if(tags==null || tags.isEmpty()) return result;
		
		for (String tag : tags) {
			if(str.indexOf(tag)>-1){
				result.add(tag);
			}
		}
		return result;
	}
	
	
	@Override
	public void update(Object obj) {

	}

	@Override
	public void delete(Object obj) {

	}

}
