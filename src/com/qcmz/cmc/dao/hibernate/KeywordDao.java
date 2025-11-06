package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IKeywordDao;
import com.qcmz.cmc.entity.CmcKeyword;
import com.qcmz.cmc.entity.CmcKeywordType;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class KeywordDao extends BaseDAO implements IKeywordDao{
	/**
	 * 获取有效的关键词
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcKeyword> findValidKeyword(){
		return (List<CmcKeyword>) qryList("from CmcKeyword t where t.cmcKeywordType.status=1");
	}
	
	/**
	 * 获取所有的关键词类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcKeywordType> findKeywordType(){
		return (List<CmcKeywordType>) qryList("from CmcKeywordType");
	}
}
