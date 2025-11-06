package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcBScene;
import com.qcmz.framework.dao.IBaseDAO;

public interface ISceneDao extends IBaseDAO {
	/**
	 * 获取所有场景
	 * @return
	 */
	public List<CmcBScene> findSceneJoinCat();
}
