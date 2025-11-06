package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IRewardBillDao;
import com.qcmz.cmc.ws.provide.vo.RewardBillBean;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class RewardBillDao extends BaseDAO implements IRewardBillDao {
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcRewardBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRewardBill t ");
		StringBuilder sbCountHql = new StringBuilder("select count(t.billid) from CmcRewardBill t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String orderBy = null;
		if(map!=null && !map.isEmpty()){
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}		
			//收支
			String incexp = map.get("incexp");
			if(NumberUtil.isNumber(incexp)){
				sbCond.append(" and t.incexp=:incexp");
				params.put("incexp", Integer.valueOf(incexp));
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.billid desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取用户账单列表
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RewardBillBean> findBillInfo(RewardBillQueryBean query){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.RewardBillBean(")
			 .append("t.billid, t.userid, t.incexp, t.billtype, t.amount, t.billdesc, t.createtime")
			 .append(") from CmcRewardBill t")
			 .append(" where t.userid=:userid ")
			 ;
		params.put("userid", query.getUid());
		
		if(query.getIncexp()!=null){
			sbHql.append(" and t.incexp=:incexp");
			params.put("incexp", query.getIncexp());
		}
		if(query.getBillType()!=null){
			sbHql.append(" and t.billtype=:billtype");
			params.put("billtype", query.getBillType());
		}
		
		if(query.getLastBillId()!=null){
			sbHql.append(" and t.billid<:billid");
			params.put("billid", query.getLastBillId());
		}
		
		sbHql.append(" order by t.billid desc");
		
		return (List<RewardBillBean>) qryListTop(sbHql.toString(), params, query.getPageSize());
	}
	
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param subServiceType
	 * @param date 日期
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, String subServiceType, Date date){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(*) from CmcRewardBill")
			 .append(" where userid=:userid and billtype=:billtype")
			 ;
		params.put("userid", userId);
		params.put("billtype", billType);
		
		if(StringUtil.notBlankAndNull(subServiceType)){
			sbHql.append(" and subservicetype=:subservicetype");
			params.put("subservicetype", subServiceType);
		}
		if(date!=null){
			sbHql.append(" and createdate=:date");
			params.put("date", date);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 是否存在
	 * @param userId
	 * @param billType
	 * @param subServiceType
	 * @param orderId
	 * @return
	 */
	public Boolean isExist(Long userId, Integer billType, String subServiceType, String orderId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select billid from CmcRewardBill")
			 .append(" where userid=:userid and billtype=:billtype")
			 .append(" and subservicetype=:subservicetype and orderid=:orderid")
			 ;
		params.put("userid", userId);
		params.put("billtype", billType);
		params.put("subservicetype", subServiceType);
		params.put("orderid", orderId);
		
		Long billId = (Long) load(sbHql.toString(), params);
		return billId!=null;
	}
}
