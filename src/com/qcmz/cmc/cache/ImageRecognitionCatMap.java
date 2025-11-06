package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcIrCat;
import com.qcmz.cmc.service.IImageRecognitionCatService;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionCatBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;

@Component
public class ImageRecognitionCatMap extends AbstractCacheMap {
	@Autowired
	private IImageRecognitionCatService imageRecognitionCatService;
	
	/**
	 * 分类缓存<catId, CmcIrCat>
	 */
	private Map<Long, CmcIrCat> map = null;
	/**
	 * 有效分类列表
	 */
	private List<ImageRecognitionCatBean> validList = null;
	
	@Override
	protected void init() {
		Map<Long, CmcIrCat> mapTemp = new HashMap<Long, CmcIrCat>();
		List<ImageRecognitionCatBean> validListTemp = new ArrayList<ImageRecognitionCatBean>();
		ImageRecognitionCatBean catBean = null;
		
		List<CmcIrCat> list = imageRecognitionCatService.findCat();
		for (CmcIrCat cat : list) {
			mapTemp.put(cat.getCatid(), cat);
			if(SystemConstants.STATUS_ON.equals(cat.getStatus())){
				catBean = new ImageRecognitionCatBean();
				catBean.setCatId(cat.getCatid());
				catBean.setCatName(cat.getCatname());
				catBean.setCatIcon(cat.getIcon());
				catBean.setCharge(cat.getCharge());
				validListTemp.add(catBean);
			}
		}
		
		map = mapTemp;
		validList = validListTemp;
	}

	/**
	 * 获取有效分类列表
	 * @return
	 */
	public List<ImageRecognitionCatBean> findValid(){
		if(safeInit(validList)){
			return validList;
		}
		return new ArrayList<ImageRecognitionCatBean>();
	}
	
	/**
	 * 获取分类信息
	 * @param catId
	 * @return
	 */
	public CmcIrCat getCat(Long catId){
		if(safeInit(map)){
			return map.get(catId);
		}
		return null;
	}
	
	/**
	 * 获取分类名称
	 * @param catId
	 * @return
	 */
	public String getCatName(Long catId){
		CmcIrCat cat = getCat(catId);
		if(cat!=null){
			return cat.getCatname();
		}
		return null;
	}
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}

}
