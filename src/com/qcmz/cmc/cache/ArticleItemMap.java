package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cms.ws.provide.locator.ArticleWS;
import com.qcmz.cms.ws.provide.vo.ArticleItemBean;
import com.qcmz.framework.cache.AbstractCacheMap;

@Component
public class ArticleItemMap extends AbstractCacheMap {
	
	private Map<Long, ArticleItemBean> map = null;
	
	@Override
	protected void init() {
		Map<Long, ArticleItemBean> mapTemp = new HashMap<Long, ArticleItemBean>(); 
		List<ArticleItemBean> list = ArticleWS.findItem();
		if(list==null) return;
		
		for (ArticleItemBean bean : list) {
			mapTemp.put(bean.getItemId(), bean);
		}
		map = mapTemp;
	}

	/**
	 * 获取商品信息
	 * @param itemId
	 * @return
	 */
	public ArticleItemBean getItem(Long itemId){
		if(safeInit(map)){
			return map.get(itemId);
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
