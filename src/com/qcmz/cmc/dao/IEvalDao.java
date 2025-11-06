package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcEvalTag;
import com.qcmz.framework.dao.IBaseDAO;

public interface IEvalDao extends IBaseDAO {
	/**
	 * 获取评价列表
	 * @return
	 */
	public List<CmcEval> findEval();
	/**
	 * 获取评价标签列表
	 * @return
	 */
	public List<CmcEvalTag> findEvalTag();
}
