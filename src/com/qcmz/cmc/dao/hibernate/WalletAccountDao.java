package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IWalletAccountDao;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 钱包帐户 
 */
@Repository
public class WalletAccountDao extends BaseDAO implements IWalletAccountDao {
	
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcWalletAccount>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcWalletAccount t ");
		StringBuilder sbCountHql = new StringBuilder("select count(t.userid) from CmcWalletAccount t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}		
			String serviceType = map.get("servicetype");
			if(StringUtil.notBlankAndNull(serviceType)){
				sbCond.append(" and t.servicetype=:servicetype");
				params.put("servicetype", serviceType);
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
		
		sbHql.append("update CmcWalletAccount t set")
			 .append("  t.balance=(select coalesce(sum(amount),0) from CmcWalletBill b where b.userid=t.userid)")
			 .append(", t.incomeamount=(select coalesce(sum(amount),0) from CmcWalletBill b where b.userid=t.userid and b.incexp=:income)")
			 .append(", t.expensesamount=(select coalesce(sum(-amount),0) from CmcWalletBill b where b.userid=t.userid and b.incexp=:expenses)")
			 .append(" where t.userid=:userid")
			 ;
		params.put("userid", userId);
		params.put("income", DictConstant.DICT_INCEXP_INCOME);
		params.put("expenses", DictConstant.DICT_INCEXP_EXPENSES);
		
		updateBatch(sbHql.toString(), params);
	}
}
