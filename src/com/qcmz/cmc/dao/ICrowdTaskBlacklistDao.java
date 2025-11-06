package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.framework.dao.IBaseDAO;

public interface ICrowdTaskBlacklistDao extends IBaseDAO {
	/**
	 * 获取所有用户编号
	 * @return
	 */
	public List<Long> findUserId();
}
