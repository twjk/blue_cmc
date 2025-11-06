package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtSubjectcat;
import com.qcmz.framework.dao.IBaseDAO;

public interface ICrowdTaskSubjectCatDao extends IBaseDAO {
	/**
	 * 获得所有题目分类
	 * @return
	 */
	public List<CmcCtSubjectcat> findAll();
}
