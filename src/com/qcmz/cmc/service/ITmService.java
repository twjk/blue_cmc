package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcTm;
import com.qcmz.cmc.entity.CmcTmVersion;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface ITmService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTm>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean);
	/**
	 * 获取所有翻译机信息
	 * @return
	 */
	public List<CmcTm> findTm();
	/**
	 * 获取翻译机信息
	 * @param tmId
	 * @return
	 */
	public CmcTm getTm(Long tmId);
	/**
	 * 获取最新版本信息
	 * @return
	 */
	public CmcTmVersion getLastVersion();
	/**
	 * 保存翻译及信息
	 * @param bean
	 * @param oper
	 */
	public void saveOrUpdate(CmcTm bean, Operator oper);
	/**
	 * 删除翻译机信息
	 * @param tmId
	 * @param oper
	 */
	public void delete(Long tmId, Operator oper);
}
