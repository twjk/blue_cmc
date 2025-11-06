package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ISceneDao;
import com.qcmz.cmc.entity.CmcBScene;
import com.qcmz.cmc.ws.provide.vo.SceneBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 场景缓存
 */
@Component
public class SceneMap extends AbstractCacheMap {
	@Autowired
	private ISceneDao sceneDao;
	
	private Map<Long, CmcBScene> map = null;
	private Map<Long, SceneBean> beanMap = null;
	private List<SceneBean> validList = null;
	
	@Override
	@PostConstruct
	protected void init() {
		Map<Long, CmcBScene> mapTemp = new HashMap<Long, CmcBScene>();
		Map<Long, SceneBean> beanMapTemp = new HashMap<Long, SceneBean>();		
		List<SceneBean> validListTemp = new ArrayList<SceneBean>();
		SceneBean sceneBean = null;
		
		List<CmcBScene> list = sceneDao.findSceneJoinCat();
		for (CmcBScene scene : list) {
			mapTemp.put(scene.getSceneid(), scene);
			
			sceneBean = new SceneBean(scene.getSceneid(), scene.getTranstype(), scene.getCatid(), scene.getCmcBScenecat().getCatname(), scene.getScenename(), scene.getIcon());
			beanMapTemp.put(scene.getSceneid(), sceneBean);
			
			if(SystemConstants.STATUS_ON.equals(scene.getStatus())){
				validListTemp.add(sceneBean);
			}
		}
		
		map = mapTemp;
		beanMap = beanMapTemp;
		validList = validListTemp;
	}

	public CmcBScene getScene(Long sceneId){
		if(safeInit(map)){
			return map.get(sceneId);
		}
		return null;
	}
	
	public SceneBean getSceneBean(Long sceneId){
		if(safeInit(beanMap)){
			return beanMap.get(sceneId);
		}
		return null;
	}
	
	public List<SceneBean> findValidScene(String transType){
		List<SceneBean> result = new ArrayList<SceneBean>();
		if(safeInit(validList)){
			if(StringUtil.isBlankOrNull(transType)){
				return validList;
			}else{
				for (SceneBean sceneBean : validList) {
					if(sceneBean.getTransType().equals(transType)){
						result.add(sceneBean);
					}
				}
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
