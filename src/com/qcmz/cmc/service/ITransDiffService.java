package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.cmc.entity.CmcTransDiff;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface ITransDiffService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);	
	/**
	 * 获取差异信息
	 * @param diffId
	 * @return
	 */
	public CmcTransDiff getDiff(Long diffId);
	/**
	 * 批量保存翻译差异
	 * @param list
	 */
	public void saveOrUpdate(List<CmcTransDiff> list);
	/**
	 * 修正译文
	 * @param diffId
	 * @param dst
	 */
	public CmcTransCorrect correctDst(Long diffId, String dst, Operator operator);
	/**
	 * 清除指定时间之前的翻译差异
	 * @param maxTime
	 */
	public void clearDiff(Date maxTime);
}
