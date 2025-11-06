package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcTransWord;
import com.qcmz.cmc.ws.provide.vo.CountryBean;
import com.qcmz.cmc.ws.provide.vo.TransWordBean;
import com.qcmz.cmc.ws.provide.vo.TransWordQueryBean;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：翻译词语
 * 修改历史：
 */
public interface ITransWordDao extends IBaseDAO {
	/**
	 * 分页获取词语列表
	 * @param search
	 * @param page
	 * @param pageSize
	 * 修改历史：
	 */
	public List<TransWordBean> findWord(TransWordQueryBean search, int page, int pageSize);
	/**
	 * 获取词语
	 * @param langCode not null
	 * @param country not null
	 * @param word not null
	 * @return
	 * 修改历史：
	 */
	public CmcTransWord getWord(String langCode, String country, String word);
	/**
	 * 获取国家列表
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public List<CountryBean> findCountry(String langCode);
}
