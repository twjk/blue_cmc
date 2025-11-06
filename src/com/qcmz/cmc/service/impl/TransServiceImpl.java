package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.dao.ITransDao;
import com.qcmz.cmc.entity.CmcTransFav;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.service.ITransService;
import com.qcmz.cmc.ws.provide.vo.TransLangBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class TransServiceImpl implements ITransService {
	
	@Autowired
	private ITransDao transDao;
	
	@Autowired	
	private IFuncCapService funcCapService;
	
	/**
	 * 获取翻译语言列表
	 * @return
	 * 修改历史：
	 */
	public List<TransLangBean> findLang(){
		List<TransLangBean> result = new ArrayList<TransLangBean>();
		TransLangBean bean = null;
		
		List<String> langs = funcCapService.findLang(BusinessConstant.FUNCID_TRANSLATE);
		for (String langCode : langs) {
			bean = new TransLangBean();
			bean.setLangCode(langCode);
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 收藏翻译结果
	 * @param uid 用户标识
	 * @param from 源语言
	 * @param src 原文
	 * @param to 目标语言
	 * @param dst 译文
	 * 修改历史：
	 */
	public void favTrans(String uid, String from, String src, String to, String dst){
		CmcTransFav entity = new CmcTransFav();
		entity.setUid(uid);
		entity.setFromlang(from);
		entity.setSrc(src);
		entity.setTolang(to);
		entity.setDst(dst);
		entity.setFavtime(new Date());
		transDao.save(entity);
	}
}
