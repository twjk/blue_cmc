package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILocalSourceDao;
import com.qcmz.cmc.entity.CmcLtSource;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class LocalSourceDao extends BaseDAO implements ILocalSourceDao {
	/**
	 * 获取所有来源
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtSource> findSource(){
		return (List<CmcLtSource>) qryList("from CmcLtSource order by sourceid");
	}
}
