package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcLtSource;
import com.qcmz.framework.dao.IBaseDAO;

public interface ILocalSourceDao extends IBaseDAO {
	/**
	 * 获取所有来源
	 * @return
	 */
	public List<CmcLtSource> findSource();
}
