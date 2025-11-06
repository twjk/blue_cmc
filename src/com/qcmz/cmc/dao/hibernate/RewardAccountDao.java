package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRewardAccountDao;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class RewardAccountDao extends BaseDAO implements IRewardAccountDao {
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcRewardAccount>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRewardAccount t ");
		StringBuilder sbCountHql = new StringBuilder("select count(t.userid) from CmcRewardAccount t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}
			//业务类型
			String serviceType = map.get("servicetype");
			if(StringUtil.notBlankAndNull(serviceType)){
				sbCond.append(" and t.servicetype=:servicetype");
				params.put("servicetype", serviceType);
			}
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}

			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		String orderby = map.get("orderby");
		if(StringUtil.notBlankAndNull(orderby)){
			sbHql.append(" order by ").append(orderby);
		}else{
			sbHql.append(" order by createtime desc");
		}
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 更新帐户金额
	 * @param userId
	 */
	public void updateAmount(Long userId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcRewardAccount t set")
			 .append("  t.balance=(select coalesce(sum(amount),0) from CmcRewardBill b where b.userid=t.userid)")
			 .append(", t.rewardtotal=(select coalesce(sum(amount),0) from CmcRewardBill b where b.userid=t.userid and b.incexp=:income and b.billtype in(:billtype))")
			 .append(" where t.userid=:userid")
			 ;
		params.put("userid", userId);
		params.put("income", DictConstant.DICT_INCEXP_INCOME);
		
		List<Integer> billTypes = new ArrayList<Integer>();
		billTypes.add(DictConstant.DICT_REWARD_BILLTYPE_RECEIVE);
		billTypes.add(DictConstant.DICT_REWARD_BILLTYPE_BESTOW);
		params.put("billtype", billTypes);
		
		updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 更新帐户状态
	 * @param userId
	 * @param status
	 */
	public void updateStatus(Long userId, Integer status){
		String hql = "update CmcRewardAccount set status=:status where userid=:userid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		params.put("status", status);

		updateBatch(hql, params);
	}
}
