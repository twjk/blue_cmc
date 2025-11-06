package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.CatWS;
import com.qcmz.bdc.ws.provide.vo.CatBean;
import com.qcmz.cmc.ws.provide.vo.CatListBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 */
@Component
public class CatMap extends AbstractCacheMap {
	/**
	 * 分类缓存，以ID为key
	 */
	private Map<String ,CatListBean> map;
	private Map<String, List<CatListBean>> typeMap;
	
	@Override
	protected void init() {
		Map<String,CatListBean> mapTemp = new HashMap<String, CatListBean>();
		Map<String, List<CatListBean>> typeMapTemp = new HashMap<String, List<CatListBean>>();
		
		List<CatListBean> temp = null;
		CatListBean listBean = null;
		List<CatBean> catList = CatWS.findCatAll();
		if(catList!=null && !catList.isEmpty()){
			for (CatBean bean : catList) {
				listBean = new CatListBean(bean.getCatId(), bean.getParentId(), bean.getCatName(), bean.getCatIcon());
				
				mapTemp.put(String.valueOf(bean.getCatId()), listBean);
				
				temp = typeMapTemp.get(bean.getCatType());
				if(temp==null) temp = new ArrayList<CatListBean>();
				temp.add(listBean);
				
				typeMapTemp.put(bean.getCatType(), temp);
				
			}
		}
		
		map = mapTemp;
		typeMap = typeMapTemp;
	}

	/**
	 * 获取分类
	 * @return
	 */
	public List<CatListBean> findCat(String catType){
		List<CatListBean> result = null;
		if(safeInit(typeMap)){
			result = typeMap.get(catType);
		}
		if(result==null){
			result = new ArrayList<CatListBean>();
		}
		return result;
	}

	public CatListBean getCat(String catId){
		if(safeInit(map)){
			return map.get(catId);
		}
		return null;
	}
	
	public Long getCatIdByName(String catType, String catName){
		if(StringUtil.isBlankOrNull(catName)) return null;
	
		List<CatListBean> list = findCat(catType);
		for (CatListBean bean : list) {
			if(bean.getCatName().equals(catName)){
				return bean.getCatId();
			}
		}
		
		return null;
	}
	
	/**
	 * 获取分类名称
	 * @param parentId
	 * @return
	 */
	public String getCatName(String catId){
		if(StringUtil.isBlankOrNull(catId) || !safeInit(map)){
			return "";			
		}
		int length = catId.length(); 
		if(length>4){
			catId = catId.substring(length-3); 
		}
		CatListBean bean = map.get(catId);
		if(bean!=null){
			return bean.getCatName();
		}
		return "";
	}
	
	/**
	 * 获取分类名称，多层级用|分隔
	 * @param catIds
	 * @return
	 */
	public String getCatNames(String catIds){
		if(map==null) init();
		if(StringUtil.isBlankOrNull(catIds)
				|| catIds.length()%4>0){
			return "";
		}
		String catId = null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<catIds.length()/4;i++){
			catId = catIds.substring(i*4, (i+1)*4);
			sb.append("|").append(getCatName(catId));
		}
		return sb.deleteCharAt(0).toString();
	}
	
	/**
	 * 获取完整的分类编号，往上推到根
	 * @param catId
	 * @return
	 */
	public String getFullCat(String catId){
		if(StringUtil.isBlankOrNull(catId) || !safeInit(map)) return null;
		
		StringBuilder sb = new StringBuilder(catId);
		CatListBean bean = map.get(catId);
		while(bean!=null && !bean.getParentId().equals(0L)){
			sb.insert(0, bean.getParentId());
			bean = map.get(bean.getParentId());
		}
		return sb.toString();
	}
	
	/** 
	 * @param obj
	 */
	@Override
	public void delete(Object obj) {}


	/** 
	 * @param obj
	 */
	@Override
	public void update(Object obj) {}

}
