package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IOrderLogDao;
import com.qcmz.cmc.entity.CmcRLog;
import com.qcmz.cmc.ws.provide.vo.OrderLogBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.StringUtil;

@Repository
public class OrderLogDao extends BaseDAO implements IOrderLogDao {
	/**
	 * 获取操作历史
	 * @param orderId
	 * @param logType
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRLog> findLog(String orderId, String logType){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcRLog where orderid=:orderid");
		params.put("orderid", orderId);
		
		if(StringUtil.notBlankAndNull(logType)){
			sbHql.append(" and logtype=:logtype");
			params.put("logtype", logType);
		}
		
		sbHql.append(" order by logid");
		
		return (List<CmcRLog>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取操作历史
	 * @param orderId
	 * @param logType
	 * @param withOperName 是否带操作人姓名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrderLogBean> findLogInfo(String orderId, String logType, boolean withOperName){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String opername = "opername";
		if(!withOperName){
			opername="''";
		}
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.OrderLogBean(")
			 .append("dealstatus, log, opertime, ").append(opername)
			 .append(")from CmcRLog where orderid=:orderid")
		;
		params.put("orderid", orderId);
		
		if(StringUtil.notBlankAndNull(logType)){
			sbHql.append(" and logtype=:logtype");
			params.put("logtype", logType);
		}
		
		sbHql.append(" order by logid");
		
		return (List<OrderLogBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 清除日志
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public void clearLog(String orderId){
		delete("delete from CmcRLog where orderid=?", orderId);
	}
}
