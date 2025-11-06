package com.qcmz.cmc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITransComboDao;
import com.qcmz.cmc.dao.ITransComboPackageDao;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboLang;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcComboPic;
import com.qcmz.cmc.entity.CmcComboPlatform;
import com.qcmz.cmc.entity.CmcComboScene;
import com.qcmz.cmc.service.ITransComboService;

@Service
public class TransComboServiceImpl implements ITransComboService{
	@Autowired
	private ITransComboDao transComboDao;
	@Autowired
	private ITransComboPackageDao transComboPackageDao;
	
	/**
	 * 获取所有套餐列表，带套餐包、图片、限定语言、限定场景、场景、适用平台
	 * @return
	 */
	public List<CmcCombo> findComboJoin(){
		List<CmcCombo> comboList = transComboDao.findCombo();
		if(comboList.isEmpty()) return comboList;
		
		Map<Long, CmcCombo> comboMap = new HashMap<Long, CmcCombo>();
		for (CmcCombo CmcCombo : comboList) {
			comboMap.put(CmcCombo.getComboid(), CmcCombo);
		}
		
		//套餐包
		List<CmcComboPackage> pkgList = transComboPackageDao.findPackage();
		for (CmcComboPackage pkg : pkgList) {
			comboMap.get(pkg.getComboid()).getPackages().add(pkg);
		}
		
		//图片
		List<CmcComboPic> picList = transComboDao.findPic();
		for (CmcComboPic pic : picList) {
			comboMap.get(pic.getComboid()).getPics().add(pic);
		}
		
		//套餐限定语言
		List<CmcComboLang> langList = transComboDao.findLang();
		for (CmcComboLang lang : langList) {
			comboMap.get(lang.getComboid()).getLangs().add(lang);
		}
		
		//套餐限定场景				
		List<CmcComboScene> sceneList = transComboDao.findSceneJoin();
		for (CmcComboScene scene : sceneList) {
			comboMap.get(scene.getComboid()).getScenes().add(scene);
		}
		
		//适用平台
		List<CmcComboPlatform> platformList = transComboDao.findPlatform();
		for (CmcComboPlatform platform : platformList) {
			comboMap.get(platform.getComboid()).getPlatforms().add(platform);
		}
		
		return comboList;
	}
}
