package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ITransUserCorrectDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTransUsercorrect>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
}
