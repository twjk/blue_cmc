package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IProxyDao;
import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.entity.CmcProxyFunc;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class ProxyDao extends BaseDAO implements IProxyDao {
	/**
	 * 获取代理列表
	 * @param funcId
	 * @param status
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProxy> findProxy(Long funcId, Integer status){
		StringBuilder sbHql = new StringBuilder("from CmcProxy t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder sbCond = new StringBuilder();
		if(funcId!=null){
			sbCond.append(" and exists(from CmcProxyFunc c where c.proxyid=t.proxyid and c.funcid=:funcid)");
			params.put("funcid", funcId);
		}
		if(status!=null){
			sbCond.append(" and status=:status");
			params.put("status", status);
		}
		if(sbCond.length()>4){
			sbCond.replace(1, 4, "where");
			sbHql.append(sbCond);
		}
		
		sbHql.append(" order by priority, proxyid");
		
		return (List<CmcProxy>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取代理功能列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProxyFunc> findProxyFunc(){
		return (List<CmcProxyFunc>) qryList("from CmcProxyFunc");
	}
	
	/**
	 * 清除代理功能 
	 * 修改历史：
	 */
	public void clearProxyFunc(){
		this.updateBatchSQL("truncate table CMC_PROXY_FUNC");
	}
}
