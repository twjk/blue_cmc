package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IEvalDao;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcEvalTag;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class EvalDao extends BaseDAO implements IEvalDao {
	/**
	 * 获取评价列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcEval> findEval(){
		return (List<CmcEval>) qryList("from CmcEval order by evaltype, evallevel");
	}
	
	/**
	 * 获取评价标签列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcEvalTag> findEvalTag(){
		return (List<CmcEvalTag>) qryList("from CmcEvalTag order by tagid");
	}
}
