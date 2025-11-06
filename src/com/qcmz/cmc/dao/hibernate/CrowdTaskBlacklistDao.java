package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ICrowdTaskBlacklistDao;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class CrowdTaskBlacklistDao extends BaseDAO implements ICrowdTaskBlacklistDao {
	/**
	 * 获取所有用户编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findUserId(){		
		return (List<Long>) qryList("select userid from CmcCtBlacklist");
	}
}
