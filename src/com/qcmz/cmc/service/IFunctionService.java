package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcFunction;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IFunctionService {
	/**
	 * 获取所有的功能列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcFunction> findAll();
}
