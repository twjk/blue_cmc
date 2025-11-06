package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IDubOrderDao;
import com.qcmz.cmc.entity.CmcRDub;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class DubOrderDao extends BaseDAO implements IDubOrderDao {
	/**
	 * 分页获取配音订单，含订单、配音员
	 * @param map
	 * @param pageBean<CmcRDub>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcRDub t left join fetch t.cmcROrder left join fetch t.cmcDubber");
		StringBuilder sbCountHql = new StringBuilder("select count(t.id) from CmcRDub t");
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
			
			String orderType = map.get("ordertype");
			if(NumberUtil.isNumber(orderType)){
				sbCond.append(" and t.cmcROrder.ordertype=:ordertype");
				params.put("ordertype", Integer.valueOf(orderType));
			}
			
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}
			
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(StringUtil.notBlankAndNull(dealstatus)){
				sbCond.append(" and t.cmcROrder.dealstatus=:dealstatus");
				params.put("dealstatus", dealstatus);
			}
			
			//创建时间
			String begin = map.get("begin");
			if(StringUtil.notBlankAndNull(begin)){
				sbCond.append(" and t.cmcROrder.createtime>=:begin");
				params.put("begin", DateUtil.parseDate(begin));
			}
			String end = map.get("end");
			if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.cmcROrder.createtime<=:end");
				params.put("end", DateUtil.setMaxTime(DateUtil.parseDate(end)));
			}
			
			//下单平台
			String platform = map.get("platform");
			if(StringUtil.notBlankAndNull(platform)){
				sbCond.append(" and t.cmcROrder.platform=:platform");
				params.put("platform", platform);
			}
			
			//作品名称
			String title = map.get("title");
			if(StringUtil.notBlankAndNull(title)){
				sbCond.append(" and t.title like :title");
				params.put("title", "%"+title+"%");
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}

		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.id desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取处理配音订单列表，含订单、配音员
	 * @param map
	 * @param pageBean<CmcRDub>
	 */
	public void findDeal(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRDub t left join fetch t.cmcROrder left join fetch t.cmcDubber");
		StringBuilder sbCountHql = new StringBuilder("select count(t.id) from CmcRDub t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbCond.append(" where t.cmcROrder.ordercat=:ordercat");
		params.put("ordercat", DictConstant.DICT_ORDERCAT_DUB);
		
		//处理进度
		String dealprogress = map.get("dealProgress");
		if(StringUtil.notBlankAndNull(dealprogress)){
			String[] arrDealProgress = StringUtil.split(dealprogress, ",");
			if(arrDealProgress.length==1){
				sbCond.append(" and t.cmcROrder.dealprogress=:dealprogress");
				params.put("dealprogress", arrDealProgress[0]);
			}else if(arrDealProgress.length>1){
				sbCond.append(" and t.cmcROrder.dealprogress in (:dealprogress)");
				params.put("dealprogress", arrDealProgress);
			}
		}else{
			sbCond.append(" and t.cmcROrder.dealprogress is not null and t.dealprogress!=''");
		}
		
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
			//订单类型
			String orderType = map.get("ordertype");
			if(NumberUtil.isNumber(orderType)){
				sbCond.append(" and t.cmcROrder.ordertype=:ordertype");
				params.put("ordertype", Integer.valueOf(orderType));
			}
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(StringUtil.notBlankAndNull(dealstatus)){
				sbCond.append(" and t.cmcROrder.dealstatus=:dealstatus");
				params.put("dealstatus", dealstatus);
			}
			//处理时间
			String begin = map.get("begin");
			if(StringUtil.notBlankAndNull(begin)){
				sbCond.append(" and t.cmcROrder.opertime>=:begin");
				params.put("begin", DateUtil.parseDate(begin));
			}
			String end = map.get("end");
			if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.cmcROrder.opertime<=:end");
				params.put("end", DateUtil.setMaxTime(DateUtil.parseDate(end)));
			}
			
		}
		
		String orderBy = null;
		if(StringUtil.isBlankOrNull(dealprogress)){
			orderBy = " order by t.cmcROrder.finishtime desc";
		}else if(DictConstants.DICT_DEALPROGRESS_DEALT.equals(dealprogress)){
			orderBy = " order by t.orderid desc";
		}else{
			orderBy = " order by t.cmcROrder.waittime";	
		}
		
		sbHql.append(sbCond).append(orderBy);
		sbCountHql.append(sbCond);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取配音订单配音信息
	 * @param orderId
	 * @return
	 */
	public CmcRDub getDub(String orderId){
		String hql = "from CmcRDub t where t.orderid=:orderid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderId);
		
		return (CmcRDub) load(hql, params);
	}
	
	/**
	 * 获取配音订单配音信息，含配音员
	 * @param orderId
	 * @return
	 */
	public CmcRDub getDubJoin(String orderId){
		String hql = "from CmcRDub t left join fetch t.cmcDubber where t.orderid=:orderid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderId);
		
		return (CmcRDub) load(hql, params);
	}
}
