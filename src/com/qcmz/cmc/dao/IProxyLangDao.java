package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcProxyLang;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProxyLangDao extends IBaseDAO {
	/**
	 * 获取所有代理语言信息
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyLang> findAll();
}
