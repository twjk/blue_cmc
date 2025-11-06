package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransComboDao;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboLang;
import com.qcmz.cmc.entity.CmcComboPic;
import com.qcmz.cmc.entity.CmcComboPlatform;
import com.qcmz.cmc.entity.CmcComboScene;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 翻译套餐
 */
@Repository
public class TransComboDao extends BaseDAO implements ITransComboDao {
	/**
	 * 获取套餐列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCombo> findCombo(){
		return (List<CmcCombo>) qryList("from CmcCombo order by sortindex asc, comboid asc");
	}
	
	/**
	 * 获取套餐图片列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcComboPic> findPic(){
		return (List<CmcComboPic>) qryList("from CmcComboPic");
	}
	
	/**
	 * 获取套餐限定使用语言列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcComboLang> findLang(){
		return (List<CmcComboLang>) qryList("from CmcComboLang");
	}
	
	/**
	 * 获取套餐限定使用场景列表，带场景信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcComboScene> findSceneJoin(){
		return (List<CmcComboScene>) qryList("from CmcComboScene t left join fetch t.cmcBScene");
	}
	
	/**
	 * 获取套餐适用平台列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcComboPlatform> findPlatform(){
		return (List<CmcComboPlatform>) qryList("from CmcComboPlatform");
	}
}
