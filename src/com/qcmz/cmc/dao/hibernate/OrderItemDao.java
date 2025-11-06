package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderItemDao;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.StringUtil;

@Repository
public class OrderItemDao extends BaseDAO implements IOrderItemDao {
	
	/**
	 * 获取商品列表
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRItem> findItem(String orderId){
		String hql = "from CmcRItem where orderid=? order by ritemid";
		return (List<CmcRItem>) qryList(hql, orderId);
	}
	
	/**
	 * 获取商品列表
	 * @param orderIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRItem> findItem(List<String> orderIds){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcRItem where orderid in(:orderids) order by ritemid";
		params.put("orderids", orderIds);
		
		return (List<CmcRItem>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取商品列表，带订单信息
	 * @param orderCat not null
	 * @param dealStatus not null
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRItem> findItemJoinOrder(Integer orderCat, String dealStatus, Date start, Date end){
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from CmcRItem t left join fetch t.cmcROrder")
			 .append(" where t.cmcROrder.ordercat=:ordercat")
			 .append(" and t.cmcROrder.dealstatus=:dealstatus")
			 .append(" and t.cmcROrder.createtime between :start and :end")
			 ;
		
		params.put("ordercat", orderCat);
		params.put("dealstatus", dealStatus);
		params.put("start", start);
		params.put("end", end);		
		
		return (List<CmcRItem>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 是否有已经购买的订单
	 * @param userId
	 * @param orderType
	 * @param itemId
	 * @param refId
	 * @return
	 */
	public boolean hasBought(Long userId, Integer orderType, Long itemId, String refId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(ritemid) from CmcRItem t")
			 .append(" where t.cmcROrder.userid=:userid")
			 .append(" and ordertype=:ordertype")
			 .append(" and t.itemid=:itemid")
			 .append(" and t.cmcROrder.dealstatus in(:dealstatus)")
			 ;
		params.put("userid", userId);
		params.put("ordertype", orderType);		
		params.put("itemid", itemId);
		
		if(StringUtil.notBlankAndNull(refId)){
			sbHql.append(" and t.refid=:refid");
			params.put("refid", refId);
		}
		
		List<String> dealstatuses = new ArrayList<String>();
		dealstatuses.add(DictConstant.DICT_ORDER_DEALSTATUS_PAID);
		dealstatuses.add(DictConstant.DICT_ORDER_DEALSTATUS_WAITDEAL);
		dealstatuses.add(DictConstant.DICT_ORDER_DEALSTATUS_SUB);
		params.put("dealstatus", dealstatuses);
		
		Long count = (Long) load(sbHql.toString(), params);
		return count>0;
	}
	
	/**
	 * 清除商品
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public void clearItem(String orderId){
		delete("delete from CmcRItem where orderid=?", orderId);
	}
}
