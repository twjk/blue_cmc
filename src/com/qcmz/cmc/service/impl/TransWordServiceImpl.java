package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITransWordDao;
import com.qcmz.cmc.entity.CmcTransWord;
import com.qcmz.cmc.service.ITransWordService;
import com.qcmz.cmc.ws.provide.vo.CountryBean;
import com.qcmz.cmc.ws.provide.vo.TransWordBean;
import com.qcmz.cmc.ws.provide.vo.TransWordQueryBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class TransWordServiceImpl implements ITransWordService {
	@Autowired
	private ITransWordDao transWordDao;
	
	/**
	 * 分页获取词语列表
	 * @param search
	 * @param page
	 * @param pageSize
	 * 修改历史：
	 */
	public List<TransWordBean> findWord(TransWordQueryBean search, int page, int pageSize){
		return transWordDao.findWord(search, page, pageSize);
	}
	
	/**
	 * 获取国家列表
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public List<CountryBean> findCountry(String langCode){
		return transWordDao.findCountry(langCode);
	}
	
	/**
	 * 保存翻译词语
	 * @param list
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<CmcTransWord> list){
		if(list==null || list.isEmpty()) return;
		
		List<CmcTransWord> result = new ArrayList<CmcTransWord>();
		CmcTransWord entity = null;
		for (CmcTransWord bean : list) {
			if(bean.getWord()==null || "".equals(bean.getWord().trim())){
				continue;
			}
			
			entity = transWordDao.getWord(bean.getLangcode(), bean.getCountry(), bean.getWord());
			if(entity==null){
				result.add(bean);
			}else{
				entity.setTranscount(entity.getTranscount()+bean.getTranscount());
				result.add(entity);
			}
		}
		if(!result.isEmpty()){
			transWordDao.saveOrUpdateAll(result);
		}
	}
}
