package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILocalTaskSpiderTaskDao;
import com.qcmz.cmc.entity.CmcLtSpdtask;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class LocalTaskSpiderTaskDao extends BaseDAO implements ILocalTaskSpiderTaskDao {
	/**
	 * 获取有效的采集任务，带来源
	 * @param maxTime 上次抓取时间<=maxTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtSpdtask> findValidTaskJoin(Date maxTime){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcLtSpdtask t left join fetch t.cmcLtSource where status=1");
		if(maxTime!=null){
			sbHql.append(" and (t.lasttime is null or t.lasttime<=:lasttime)");
			params.put("lasttime", maxTime);
		}
		sbHql.append(" order by lasttime, spdtaskid");
		
		return (List<CmcLtSpdtask>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 更新采集结果
	 * @param spdTaskId
	 * @param lastIdentify
	 */
	public void updateSpiderResult(Long spdTaskId, String lastIdentify){
		String hql = "update CmcLtSpdtask set lasttime=:lasttime, lastidentify=:lastidentify where spdtaskid=:spdtaskid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lasttime", new Date());
		params.put("lastidentify", lastIdentify);
		params.put("spdtaskid", spdTaskId);
		
		updateBatch(hql, params);
	}
}
