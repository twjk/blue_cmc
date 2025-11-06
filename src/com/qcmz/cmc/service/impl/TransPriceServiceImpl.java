package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITransPriceDao;
import com.qcmz.cmc.entity.CmcTpIntl;
import com.qcmz.cmc.entity.CmcTpLang;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.cmc.service.ITransPriceService;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class TransPriceServiceImpl implements ITransPriceService {
	@Autowired
	private ITransPriceDao transPriceDao;
	
	/**
	 * 获取翻译价格列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpPrice> findPrice(){
		return transPriceDao.findPrice();
	}
	
	/**
	 * 获取翻译价格国际化列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpIntl> findPriceIntl(){
		return transPriceDao.findPriceIntl();
	}
	
	/**
	 * 获取翻译价格语言列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpLang> findPriceLang(){
		return transPriceDao.findPriceLang();
	}
}
