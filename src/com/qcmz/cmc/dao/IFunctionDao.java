package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcFunction;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IFunctionDao extends IBaseDAO {
	/**
	 * 获取所有的功能列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcFunction> findAll();
}
