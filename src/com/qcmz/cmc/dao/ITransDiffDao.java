package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.Map;

import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ITransDiffDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 清除指定时间之前的翻译差异
	 * @param maxTime
	 */
	public void clearDiff(Date maxTime);
}
