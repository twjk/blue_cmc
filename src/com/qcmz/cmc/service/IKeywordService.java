package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcKeyword;
import com.qcmz.cmc.entity.CmcKeywordType;

public interface IKeywordService {
	/**
	 * 获取有效的关键词
	 * @return
	 */
	public List<CmcKeyword> findValidKeyword();
	/**
	 * 获取所有的关键词类型
	 * @return
	 */
	public List<CmcKeywordType> findKeywordType();
}
