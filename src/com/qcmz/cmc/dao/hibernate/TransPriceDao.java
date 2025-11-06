package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransPriceDao;
import com.qcmz.cmc.entity.CmcTpIntl;
import com.qcmz.cmc.entity.CmcTpLang;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class TransPriceDao extends BaseDAO implements ITransPriceDao {
	
	/**
	 * 获取翻译价格列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTpPrice> findPrice(){
		return (List<CmcTpPrice>) qryList("from CmcTpPrice order by transway desc");
	}
	
	/**
	 * 获取翻译价格国际化列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTpIntl> findPriceIntl(){
		return (List<CmcTpIntl>) qryList("from CmcTpIntl");
	}
	
	/**
	 * 获取翻译价格语言列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTpLang> findPriceLang(){
		return (List<CmcTpLang>) qryList("from CmcTpLang");
	}
}