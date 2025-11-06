package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransComboPackageDao;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class TransComboPackageDao extends BaseDAO implements ITransComboPackageDao {
	/**
	 * 获取套餐包列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcComboPackage> findPackage(){
		return (List<CmcComboPackage>) qryList("from CmcComboPackage order by num, pkgid");
	}
}
