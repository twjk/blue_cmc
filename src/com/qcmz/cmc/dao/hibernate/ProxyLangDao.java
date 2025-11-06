package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IProxyLangDao;
import com.qcmz.cmc.entity.CmcProxyLang;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class ProxyLangDao extends BaseDAO implements IProxyLangDao {
	/**
	 * 获取所有代理语言信息
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProxyLang> findAll(){
		return (List<CmcProxyLang>) qryList("from CmcProxyLang");
	}
}
