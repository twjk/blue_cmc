package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcTransExample;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITransExampleService {
	/**
	 * 批量保存
	 * @param list
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<CmcTransExample> list);
}
