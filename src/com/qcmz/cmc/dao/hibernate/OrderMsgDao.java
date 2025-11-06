package com.qcmz.cmc.dao.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.IOrderMsgDao;
import com.qcmz.cmc.entity.CmcRMsg;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class OrderMsgDao extends BaseDAO implements IOrderMsgDao {
	/**
	 * 以订单为单位分页获取最新留言列表，带订单信息
	 * @param sideType
	 * @param userId
	 * @param orderId 结束以
	 * @param pageBean<CmcRMsg>
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void findRecentMsg(String sideType, Long userId, String orderId, PageBean pageBean){
		StringBuilder sbSql = new StringBuilder();
		StringBuilder sbCountSql = new StringBuilder();
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(userId!=null){
			sbSql.append("select max(t.msgid) from CMC_R_MSG t inner join CMC_R_ORDER r on t.orderid=r.orderid");
		}else{
			sbSql.append("select max(t.msgid) from CMC_R_MSG t");
		}
		
		//订单号
		if(StringUtil.notBlankAndNull(orderId)){
			if(orderId.length()==18){
				sbCond.append(" and t.orderid = :orderid");
				params.put("orderid", orderId);
			}else{
				sbCond.append(" and t.orderid like :orderid");
				params.put("orderid", "%"+orderId);
			}
		}
		
		//用户编号
		if(userId!=null){
			sbCond.append(" and r.userid=:userid");
			params.put("userid", userId);
		}
		
		//留言方
		if(StringUtil.notBlankAndNull(sideType)){
			sbCond.append(" and t.sidetype=:sidetype");
			params.put("sidetype", sideType);
		}
		
		if(sbCond.length()>0){
			sbCond.replace(0, 5, " where ");
			sbSql.append(sbCond);
		}			
			
		sbSql.append(" group by t.orderid");

		sbCountSql.append("select count(*) from(").append(sbSql).append(") t2");
		
		sbSql.append(" order by 1 desc");

		getQuerySQLPageBean(sbSql.toString(), sbCountSql.toString(), params, pageBean);
		
		List<BigInteger> temp = (List<BigInteger>) pageBean.getResultList();
		if(!temp.isEmpty()){
			params.clear();
			List<Long> ids = new ArrayList<Long>();
			for (BigInteger id : temp) {
				ids.add(id.longValue());
			}
			params.put("ids", ids);
			List<CmcRMsg> resultList = (List<CmcRMsg>) qryListByMap("from CmcRMsg t left join fetch t.cmcROrder where t.msgid in(:ids) order by t.msgid desc", params);
			pageBean.setResultList(resultList);
		}
	}
	
	/**
	 * 获取指定订单的留言列表
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRMsg> findMsg(String orderId){
		return (List<CmcRMsg>) qryList("from CmcRMsg where orderid=? order by msgid desc", orderId);
	}
	
	/**
	 * 分页获取留言列表，带订单信息，倒序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRMsg> findMsg(String orderId, Long userId, int pageSize, Long lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("from CmcRMsg t left join fetch t.cmcROrder where t.orderid=:orderid ");
		params.put("orderid", orderId);
		
		if(userId!=null){
			sbHql.append(" and t.cmcROrder.userid=:userid");
			params.put("userid", userId);
		}
		
		if(lastId!=null){
			sbHql.append(" and t.msgid<:msgid");
			params.put("msgid", lastId);
		}
		
		
				
		sbHql.append(" order by t.msgid desc");
		
		return (List<CmcRMsg>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 分页获取新的留言列表， 带订单信息，正序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRMsg> findLastMsg(String orderId, Long userId, int pageSize, Long lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("from CmcRMsg t left join fetch t.cmcROrder where t.orderid=:orderid ");
		params.put("orderid", orderId);
		
		if(userId!=null){
			sbHql.append(" and t.cmcROrder.userid=:userid");
			params.put("userid", userId);
		}
		
		if(lastId!=null){
			sbHql.append(" and t.msgid>:msgid");
			params.put("msgid", lastId);
		}
				
		sbHql.append(" order by t.msgid");
		
		return (List<CmcRMsg>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 清除留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public void clearMsg(String orderId){
		delete("delete from CmcRMsg where orderid=?", orderId);
	}
}
