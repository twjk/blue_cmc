package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IWalletBillDao;
import com.qcmz.cmc.entity.CmcWalletBill;
import com.qcmz.cmc.ws.provide.vo.WalletBillBean;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 钱包账单
 */
@Repository
public class WalletBillDao extends BaseDAO implements IWalletBillDao {

	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcWalletBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcWalletBill t ");
		StringBuilder sbCountHql = new StringBuilder("select count(t.billid) from CmcWalletBill t");
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
	 * 获取用户最新账单
	 * @param userIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcWalletBill> findLastBill(Set<Long> userIds){
		if(userIds==null || userIds.isEmpty()) return new ArrayList<CmcWalletBill>();
		
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcWalletBill where billid in(")
			 .append("select max(billid) from CmcWalletBill where userid in(:userids) group by userid")
			 .append(")")
			 ;
		params.put("userids", userIds);
		
		return (List<CmcWalletBill>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 分页获取账单列表
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WalletBillBean> findBillInfo(WalletBillQueryBean query){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.WalletBillBean(")
			 .append("t.billid, t.userid, t.incexp, t.billtype, t.amount, t.billdesc, t.createtime")
			 .append(") from CmcWalletBill t")
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
		
		return (List<WalletBillBean>) qryListTop(sbHql.toString(), params, query.getPageSize());
	}
	
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param date
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, Date date){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(billid) from CmcWalletBill")
			 .append(" where userid=:userid and billtype=:billtype")
			 ;
		params.put("userid", userId);
		params.put("billtype", billType);
		
		if(date!=null){
			sbHql.append(" and createdate=:date");
			params.put("date", date);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户账单
	 * @param userId not null
	 * @param orderId not null
	 * @param billType not null
	 */
	public CmcWalletBill getBill(Long userId, String orderId, Integer billType){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcWalletBill where userid=:userid")
			 .append(" and orderid=:orderid")
			 .append(" and billtype=:billtype")
			 ;
		params.put("userid", userId);
		params.put("orderid", orderId);
		params.put("billtype", billType);
		
		return (CmcWalletBill) load(sbHql.toString(), params);
	}
}
