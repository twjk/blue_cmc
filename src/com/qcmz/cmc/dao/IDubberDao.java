package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcDubber;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IDubberDao extends IBaseDAO {
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
	 * 更新状态
	 * @param dubberId
	 * @param status
	 */
	public void updateStatus(Long dubberId, Integer status);
}
