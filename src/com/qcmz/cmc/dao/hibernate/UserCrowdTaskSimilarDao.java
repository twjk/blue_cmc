package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IUserCrowdTaskSimilarDao;
import com.qcmz.cmc.entity.CmcCtSimilar;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class UserCrowdTaskSimilarDao extends BaseDAO implements IUserCrowdTaskSimilarDao{
	/**
	 * 获取相似列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtSimilar> findSimilar(Integer contentType){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtSimilar");
		
		if(contentType!=null){
			sbHql.append(" where contenttype=:contenttype");
			params.put("contenttype", contentType);
		}
		sbHql.append(" order by id");
		
		return (List<CmcCtSimilar>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取数量
	 * @param similarUserId
	 * @param contentType
	 * @return
	 */
	public Long getCountBySimilarUserId(Long similarUserId, Integer contentType){
		String hql = "select count(id) from CmcCtSimilar where contenttype=:contenttype and similaruserid=:similaruserid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contenttype", contentType);
		params.put("similaruserid", similarUserId);
		return (Long) load(hql, params);		
	}
}
