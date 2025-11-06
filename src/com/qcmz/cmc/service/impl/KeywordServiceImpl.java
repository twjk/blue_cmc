package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IKeywordDao;
import com.qcmz.cmc.entity.CmcKeyword;
import com.qcmz.cmc.entity.CmcKeywordType;
import com.qcmz.cmc.service.IKeywordService;

@Service
public class KeywordServiceImpl implements IKeywordService {
	@Autowired
	private IKeywordDao keywordDao;
	
	/**
	 * 获取有效的关键词
	 * @return
	 */
	public List<CmcKeyword> findValidKeyword(){
		return keywordDao.findValidKeyword();
	}
	
	/**
	 * 获取所有的关键词类型
	 * @return
	 */
	public List<CmcKeywordType> findKeywordType(){
		return keywordDao.findKeywordType();
	}
	
}
