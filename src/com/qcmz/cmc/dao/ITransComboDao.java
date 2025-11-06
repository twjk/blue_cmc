package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboLang;
import com.qcmz.cmc.entity.CmcComboPic;
import com.qcmz.cmc.entity.CmcComboPlatform;
import com.qcmz.cmc.entity.CmcComboScene;
import com.qcmz.framework.dao.IBaseDAO;

public interface ITransComboDao extends IBaseDAO {
	/**
	 * 获取套餐列表
	 * @return
	 */
	public List<CmcCombo> findCombo();
	/**
	 * 获取套餐图片列表
	 * @return
	 */
	public List<CmcComboPic> findPic();
	/**
	 * 获取套餐限定使用语言列表
	 * @return
	 */
	public List<CmcComboLang> findLang();
	/**
	 * 获取套餐限定使用场景列表，带场景信息
	 * @return
	 */
	public List<CmcComboScene> findSceneJoin();
	/**
	 * 获取套餐适用平台列表
	 * @return
	 */
	public List<CmcComboPlatform> findPlatform();
}
