package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IRewardCashDao;
import com.qcmz.cmc.entity.CmcRewardCash;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class RewardCashDao extends BaseDAO implements IRewardCashDao {
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRewardCash>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRewardCash t");
		StringBuilder sbCountHql = new StringBuilder("select count(cashid) from CmcRewardCash t");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String orderby = " order by cashid desc";
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			
			//查询条件
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
			//业务类型
			String serviceType = map.get("servicetype");
			if(StringUtil.notBlankAndNull(serviceType)){
				sbCond.append(" and t.cmcRewardAccount.servicetype=:servicetype");
				params.put("servicetype", serviceType);
			}
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				Integer st = Integer.valueOf(status);
				sbCond.append(" and t.status=:status");
				params.put("status", st);
				if(DictConstants.DICT_CASHSTATUS_APPLY.equals(st)){
					orderby = " order by cashid";
				}
			}
			
			if(sbCond.length()>3){
				sbCond.replace(1, 4, "where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(orderby);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取待打款的提现编号
	 * @param pageSize not null
	 * @param maxId not null
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findCashId4Transfer(int pageSize, Long maxId, Long lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select cashid from CmcRewardCash t where status=:status and cashid<=:maxid");
		params.put("status", DictConstants.DICT_CASHSTATUS_APPLY);
		params.put("maxid", maxId);		
		
		if(lastId!=null){
			sbHql.append(" and cashid>:cashid");
			params.put("cashid", lastId);
		}
		
		sbHql.append(" order by t.cashid");
		
		return (List<Long>) qryListTop(sbHql.toString(), params, pageSize);		
	}
	
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRewardCash> findCash(Long userId, Integer status){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcRewardCash where userid=:userid");
		params.put("userid", userId);
		
		if(status!=null){
			sbHql.append(" and status=:status");
			params.put("status", status);
		}
	
		sbHql.append(" order by cashid desc");
		
		return (List<CmcRewardCash>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取提现信息，带cmcRewardAccount
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCashJoinAccount(Long cashId){
		String hql = "from CmcRewardCash t left join fetch t.cmcRewardAccount where t.cashid=:cashid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cashid", cashId);
		
		return (CmcRewardCash) load(hql, params);
	}
	
	/**
	 * 获取提现数
	 * @param status not null
	 * @return
	 */
	public Long getCashCount(Integer status){
		String hql = "select count(cashid) from CmcRewardCash where status=:status";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		
		return (Long) load(hql, params);
	}
}
