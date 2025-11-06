package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcTransWord;
import com.qcmz.cmc.ws.provide.vo.CountryBean;
import com.qcmz.cmc.ws.provide.vo.TransWordBean;
import com.qcmz.cmc.ws.provide.vo.TransWordQueryBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITransWordService {
	/**
	 * 分页获取词语列表
	 * @param search
	 * @param page
	 * @param pageSize
	 * 修改历史：
	 */
	public List<TransWordBean> findWord(TransWordQueryBean search, int page, int pageSize);
	/**
	 * 获取国家列表
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public List<CountryBean> findCountry(String langCode);
	/**
	 * 保存翻译词语
	 * @param list
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<CmcTransWord> list);
}
