package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcProxyLang;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProxyLangService {
	/**
	 * 获取所有代理语言信息
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyLang> findAll();
}
