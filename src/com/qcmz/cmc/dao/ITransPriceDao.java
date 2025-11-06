package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcTpIntl;
import com.qcmz.cmc.entity.CmcTpLang;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITransPriceDao extends IBaseDAO {
	/**
	 * 获取翻译价格列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpPrice> findPrice();
	
	/**
	 * 获取翻译价格国际化列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpIntl> findPriceIntl();
	/**
	 * 获取翻译价格语言列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpLang> findPriceLang();
}