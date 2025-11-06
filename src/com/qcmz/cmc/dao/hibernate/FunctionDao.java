package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IFunctionDao;
import com.qcmz.cmc.entity.CmcFunction;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：功能维护
 * 修改历史：
 */
@Repository
public class FunctionDao extends BaseDAO implements IFunctionDao {
	/**
	 * 获取所有的功能列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcFunction> findAll(){
		return (List<CmcFunction>) qryList("from CmcFunction");
	}
}
