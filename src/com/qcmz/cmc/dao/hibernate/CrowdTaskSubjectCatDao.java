package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ICrowdTaskSubjectCatDao;
import com.qcmz.cmc.entity.CmcCtSubjectcat;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class CrowdTaskSubjectCatDao extends BaseDAO implements ICrowdTaskSubjectCatDao {
	/**
	 * 获得所有题目分类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtSubjectcat> findAll(){
		return (List<CmcCtSubjectcat>) qryList("from CmcCtSubjectcat order by catid");
	}
}
