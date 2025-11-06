package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IWalletRechargeDao;
import com.qcmz.cmc.entity.CmcWalletRecharge;
import com.qcmz.cmc.vo.WalletRechargeCountBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 充值 
 */
@Repository
public class WalletRechargeDao extends BaseDAO implements IWalletRechargeDao {
	
	/**
	 * 分页获取充值列表，带帐户
	 * @param map
	 * @param pageBean<CmcWalletRecharge>
	 * @return 
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcWalletRecharge t left join fetch t.cmcWalletAccount a");
		StringBuilder sbCountHql = new StringBuilder("select count(t.orderid) from CmcWalletRecharge t left join t.cmcWalletAccount a");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String orderBy = null;
		if(map!=null && !map.isEmpty()){
			//订单编号
			String orderid = map.get("orderid");
			if(StringUtil.notBlankAndNull(orderid)){
				if(orderid.length()==18){
					sbCond.append(" and t.orderid = :orderid");
					params.put("orderid", orderid);
				}else{
					sbCond.append(" and t.orderid like :orderid");
					params.put("orderid", "%"+orderid);
				}
			}
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}	
			//业务类型
			String serviceType = map.get("servicetype");
			if(StringUtil.notBlankAndNull(serviceType)){
				sbCond.append(" and t.cmcWalletAccount.servicetype=:servicetype");
				params.put("servicetype", serviceType);
			}
			//用户编号
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
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.createtime desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 充值统计
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WalletRechargeCountBean> findRechargeCount(Date start, Date end){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.vo.WalletRechargeCountBean(")
			 .append("status, count(orderid), sum(amount), sum(payamount)")
			 .append(") from CmcWalletRecharge")
			 .append(" where createtime between :start and :end")			 
		;
		params.put("start", start);
		params.put("end", end);
		
		sbHql.append(" group by status");
		
		return (List<WalletRechargeCountBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取充值信息，带帐户
	 * @param orderId
	 * @return
	 */
	public CmcWalletRecharge getRechargeJoinAccount(String orderId){
		String hql = "from CmcWalletRecharge t left join fetch t.cmcWalletAccount where t.orderid=?";
		return (CmcWalletRecharge)load(hql, orderId);
	}
	
	/**
	 * 更新充值状态
	 * @param orderId
	 * @param status
	 */
	public void updateStatus(String orderId, Integer status){
		String hql = "update CmcWalletRecharge set status=? where orderid=?";
		update(hql, new Object[]{status, orderId});
	}
}
