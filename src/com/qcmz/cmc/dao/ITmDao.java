package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcTm;
import com.qcmz.cmc.entity.CmcTmVersion;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ITmDao extends IBaseDAO {
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
	 * 获取最新版本信息
	 * @return
	 */
	public CmcTmVersion getLastVersion();
	/**
	 * 获取翻译机信息
	 * @param tmCode
	 * @return
	 */
	public CmcTm getTm(String tmCode);
}
