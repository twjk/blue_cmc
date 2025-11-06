package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IProxyLangDao;
import com.qcmz.cmc.entity.CmcProxyLang;
import com.qcmz.cmc.service.IProxyLangService;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class ProxyLangServiceImpl implements IProxyLangService {
	@Autowired
	private IProxyLangDao proxyLangDao;
	
	/**
	 * 获取所有代理语言信息
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyLang> findAll(){
		return proxyLangDao.findAll();
	}
}
