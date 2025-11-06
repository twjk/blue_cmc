package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IUserCrowdTaskQcDao;
import com.qcmz.cmc.entity.CmcCtQc;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.DateUtil;

@Repository
public class UserCrowdTaskQcDao extends BaseDAO implements IUserCrowdTaskQcDao {
	
	/**
	 * 获取用户任务审校列表
	 * @param utId
	 * @param qcStatus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtQc> findQc(String utId, Integer qcStatus){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtQc where utid=:utid");
		params.put("utid", utId);
		
		if(qcStatus!=null){
			sbHql.append(" and qcstatus=:qcstatus");
			params.put("qcstatus", qcStatus);
		}
		
		return (List<CmcCtQc>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取待发放审校奖励的列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findQc4GrantQcReward(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select qcid from CmcCtQc where qcrewardstatus=:status");
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		
		/*
		//延时发放奖励
		sbHql.append(" and qcfinishtime<=:time");
		params.put("time", DateUtil.add(new Date(), DateUtil.HOUR, -20));
		*/
		
		return (List<Long>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取超时未发放审校奖励的数量
	 * @return
	 */
	public Long getCount4UngrantQcReward(){
		String hql = "select count(qcid) from CmcCtQc where qcrewardstatus=:status and qcfinishtime<=:time";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		params.put("time", DateUtil.addDay(new Date(), -1));
		
		return (Long) load(hql, params);
	}
	
	/**
	 * 获取用户任务审校
	 * @param utId not null
	 * @param qcStatus not null
	 * @return
	 */
	public CmcCtQc getQc(String utId, Integer qcStatus){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcCtQc where utid=:utid and qcstatus=:qcstatus";
		params.put("utid", utId);
		params.put("qcstatus", qcStatus);
		
		return (CmcCtQc) load(hql, params);
	}
	
	/**
	 * 获取用户任务审校
	 * @param utId not null
	 * @param qcStatus not null
	 * @return
	 */
	public CmcCtQc getQc(String utId, List<Integer> qcStatuses){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcCtQc where utid=:utid and qcstatus in(:qcstatus)";
		params.put("utid", utId);
		params.put("qcstatus", qcStatuses);
		
		return (CmcCtQc) load(hql, params);
	}
	
	
	/**
	 * 更新用户任务审校已审校题目数
	 * @param qcId
	 */
	public void updateQcFinishNum(Long qcId){		
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcCtQc qc set qcfinishnum=(")
			 .append("select count(usid) from CmcCtUsersubject us where us.utid=qc.utid and us.qcid=qc.qcid and us.qcstatus=:qcstatus")
			 .append(") where qcid=:qcid")
			 ;
		
		params.put("qcstatus", DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT);
		params.put("qcid", qcId);
		
		updateBatch(sbHql.toString(), params);
	}
}
