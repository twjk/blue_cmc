package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboLang;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcComboPic;
import com.qcmz.cmc.entity.CmcComboPlatform;
import com.qcmz.cmc.entity.CmcComboScene;
import com.qcmz.cmc.service.ITransComboService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.SceneBean;
import com.qcmz.cmc.ws.provide.vo.TransComboBean;
import com.qcmz.cmc.ws.provide.vo.TransComboLangBean;
import com.qcmz.cmc.ws.provide.vo.TransComboPicBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;

/**
 * 翻译套餐缓存
 */
@Component
public class TransComboMap extends AbstractCacheMap {
	@Autowired
	private ITransComboService transComboService;
	
	/**
	 * 套餐信息<comboid, CmcCombo>
	 */
	private Map<Long, CmcCombo> comboMap = null;
	/**
	 * 套餐适用平台<comboid, List<CmcComboPlatform>>
	 */
	private Map<Long, List<CmcComboPlatform>> comboPlatformMap = null;

	/**
	 * 有效套餐列表，带包信息
	 */
	private List<TransComboBean> comboValidList = null;
	
	/**
	 * 套餐包信息<pkgid, CmcComboPackage>
	 */
	private Map<Long, CmcComboPackage> pkgMap = null;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcCombo> comboMapTemp = new HashMap<Long, CmcCombo>();
		Map<Long, CmcComboPackage> pkgMapTemp = new HashMap<Long, CmcComboPackage>();	
		Map<Long, List<CmcComboPlatform>> comboPlatformMapTemp = new HashMap<Long, List<CmcComboPlatform>>();
		
		List<TransComboBean> comboValidListTemp = new ArrayList<TransComboBean>();
		TransComboBean comboBean = null;
		
		List<CmcCombo> list = transComboService.findComboJoin();
		for (CmcCombo combo : list) {
			//套餐信息
			comboMapTemp.put(combo.getComboid(), combo);
			comboPlatformMapTemp.put(combo.getComboid(), combo.getPlatforms());
			
			for (CmcComboPackage pkg : combo.getPackages()) {
				pkgMapTemp.put(pkg.getPkgid(), pkg);
			}
			
			if(SystemConstants.STATUS_ON.equals(combo.getStatus())){
				//封装套餐信息
				comboBean = BeanConvertUtil.toTransComboBean(combo);
				
				//封装套餐包信息
				for (CmcComboPackage pkg : combo.getPackages()) {
					if(SystemConstants.STATUS_ON.equals(pkg.getStatus())){
						comboBean.getPkgs().add(BeanConvertUtil.toTransComboPackageBean(combo, pkg));
					}
				}
				
				//封装图片
				for (CmcComboPic pic : combo.getPics()) {
					comboBean.getPics().add(new TransComboPicBean(pic.getPicurl()));
				}
				
				//封装语言信息
				for (CmcComboLang lang : combo.getLangs()) {
					comboBean.getLangs().add(new TransComboLangBean(lang.getFromlang(), lang.getTolang()));
				}
				
				//封装场景信息
				for (CmcComboScene scene : combo.getScenes()) {
					comboBean.getScenes().add(new SceneBean(scene.getCmcBScene().getSceneid(), scene.getCmcBScene().getTranstype(), scene.getCmcBScene().getScenename(), scene.getCmcBScene().getIcon()));
				}
				
				comboValidListTemp.add(comboBean);
			}
		}
		
		pkgMap = pkgMapTemp;
		comboMap = comboMapTemp;
		comboValidList = comboValidListTemp;
		comboPlatformMap = comboPlatformMapTemp;
	}

	/**
	 * 获取套餐信息，带套餐包、限定语言、限定场景
	 * @param comboId
	 * @return
	 */
	public CmcCombo getCombo(Long comboId){
		if(comboId!=null && safeInit(comboMap)){
			return comboMap.get(comboId);
		}
		return null;
	}
	
	public CmcComboPackage getPackage(Long pkgId){
		if(pkgId!=null && safeInit(pkgMap)){
			return pkgMap.get(pkgId);
		}
		return null;
	}
	
	public List<CmcComboPlatform> findPlatform(Long comboId){
		if(comboId!=null && safeInit(comboPlatformMap)){
			return comboPlatformMap.get(comboId);
		}
		return null;
	}
	
	/**
	 * 获取有效的翻译套餐列表
	 * @return
	 */
	public List<TransComboBean> findValidComboInfo(){
		if(safeInit(comboValidList)){
			return comboValidList;
		}
		return new ArrayList<TransComboBean>();
	}
	
	/**
	 * 获取有效的翻译套餐列表
	 * @return
	 */
	public List<TransComboBean> findValidComboInfo(String transType){
		if(safeInit(comboValidList)){
			return comboValidList;
		}
		return new ArrayList<TransComboBean>();
	}
			
	/**
	 * 获取有效的套餐信息
	 * @param comboId
	 */
	public TransComboBean getValidComboInfo(Long comboId){
		if(comboId==null || !safeInit(comboValidList)){
			return null;
		}
		
		Long now = System.currentTimeMillis();
		for (TransComboBean bean : comboValidList) {
			if(bean.getComboId().equals(comboId)){
				if(now>=bean.getStartTime() && now<=bean.getEndTime()){
					return bean;
				}
			}
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
