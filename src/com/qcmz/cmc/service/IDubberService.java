package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcDubber;
import com.qcmz.framework.page.PageBean;

public interface IDubberService {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcDubber>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取有效的配音员
	 * @param catId
	 * @return
	 */
	public List<CmcDubber> findValidDubber(Long catId);
	/**
	 * 获取配音员
	 * @param dubberId
	 */
	public CmcDubber getDubber(Long dubberId);
	/**
	 * 保存配音员
	 * @param bean
	 */
	public void saveOrUpdateDubber(CmcDubber bean);
	/**
	 * 更新状态
	 * @param dubberId
	 * @param status
	 */
	public void updateStatus(Long dubberId, Integer status);
}
