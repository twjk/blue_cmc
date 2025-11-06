package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.framework.dao.IBaseDAO;

public interface ITransComboPackageDao extends IBaseDAO {
	/**
	 * 获取套餐包列表
	 * @return
	 */
	public List<CmcComboPackage> findPackage();
}
