package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ITransLogDao;
import com.qcmz.cmc.entity.CmcTransLog;
import com.qcmz.cmc.vo.TransLogIdRange;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：翻译日志
 * 修改历史：
 */
@Component
public class TransLogDao extends BaseDAO implements ITransLogDao {
	
	/**
	 * 获取指定时间范围内最小和最大日志编号
	 * @param start
	 * @param end
	 * @return
	 * 修改历史：
	 */
	public TransLogIdRange getIdRange(Date start, Date end){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sbHql = new StringBuilder();
		
		sbHql.append("select new com.qcmz.cmc.vo.TransLogIdRange(min(logid), max(logid))")
			 .append("from CmcTransLog where starttime between :start and :end")
		;
		params.put("start", start);
		params.put("end", end);
		
		return (TransLogIdRange) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取日志列表
	 * @param lastId
	 * @param maxId
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTransLog> findLog(Long lastId, Long maxId, int pageSize){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", lastId);
		params.put("maxId", maxId);
		
		String hql = "from CmcTransLog where logid>:lastId and logid<=:maxId order by logid";
		
		return (List<CmcTransLog>) qryListTop(hql, params, pageSize);
	}
	
	/**
	 * 分页获取日志列表
	 * @param from
	 * @param lastId
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTransLog> findLog(String from, Long lastId, int pageSize){
		StringBuilder sbHql = new StringBuilder("from CmcTransLog");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		if(StringUtil.notBlankAndNull(from)){
			sbCond.append(" and fromlang=:fromlang");
			params.put("fromlang", from);
		}
		
		if(lastId!=null){
			sbCond.append(" and logid>:lastId");
			params.put("lastId", lastId);
		}
		
		if(sbCond.length()>0){
			sbCond.replace(1, 4, "where");
			sbHql.append(sbCond);
		}
		
		sbHql.append(" order by logid");
		
		return (List<CmcTransLog>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 删除日志
	 * @param minId
	 * @param maxId
	 * 修改历史：
	 */
	public void deleteLog(Long minId, Long maxId){
		delete("delete from CmcTransLog where logid between ? and ? ", new Object[]{minId, maxId});
	}
}
