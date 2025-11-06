package com.qcmz.cmc.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderDao;
import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.vo.OrderCountBean;
import com.qcmz.cmc.vo.OrderDayCountBean;
import com.qcmz.cmc.ws.provide.vo.OrderListBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class OrderDao extends BaseDAO implements IOrderDao {
	/**
	 * 分页获取订单列表，带用户套餐
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcROrder t left join fetch t.cmcUCombo");
		StringBuilder sbCountHql = new StringBuilder("select count(t.orderid) from CmcROrder t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String orderBy = null;
		if(map!=null && !map.isEmpty()){
			sbCond.append(" where 1=1");
			
			//订单分类
			String ordercat = map.get("ordercat");
			if(NumberUtil.isNumber(ordercat)){
				sbCond.append(" and t.ordercat=:ordercat");
				params.put("ordercat", Integer.valueOf(ordercat));
			}
			
			//订单类型
			String ordertype = map.get("ordertype");
			if(NumberUtil.isNumber(ordertype)){
				sbCond.append(" and t.ordertype=:ordertype");
				params.put("ordertype", Integer.valueOf(ordertype));
			}
			
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}
			
			//用户类型
			String usertype = map.get("usertype");
			if(NumberUtil.isNumber(usertype)){
				sbCond.append(" and t.usertype=:usertype");
				params.put("usertype", Integer.valueOf(usertype));
			}
			
			String isUseCoupon = map.get("isUseCoupon");
			if(StringUtil.notBlankAndNull(isUseCoupon)){
				boolean use = Boolean.valueOf(isUseCoupon);
				if(use){
					sbCond.append(" and t.couponamount>0");
				}else{
					sbCond.append(" and t.couponamount=0");
				}
			}
			
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(StringUtil.notBlankAndNull(dealstatus)){
				sbCond.append(" and t.dealstatus=:dealstatus");
				params.put("dealstatus", dealstatus);
			}
			
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
			
			//提交时间
			String begin = map.get("begin");
			if(StringUtil.notBlankAndNull(begin)){
				sbCond.append(" and t.createtime>=:begin");
				params.put("begin", DateUtil.parseDate(begin));
			}
			String end = map.get("end");
			if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.createtime<=:end");
				params.put("end", DateUtil.setMaxTime(DateUtil.parseDate(end)));
			}
			
			//业务类型
			String servicetype = map.get("servicetype");
			if(StringUtil.notBlankAndNull(servicetype)){
				sbCond.append(" and t.servicetype=:servicetype");
				params.put("servicetype", servicetype);
			}
			
			//下单平台
			String platform = map.get("platform");
			if(StringUtil.notBlankAndNull(platform)){
				sbCond.append(" and t.platform=:platform");
				params.put("platform", platform);
			}
			
			//译员
			String oper = map.get("oper");
			if(StringUtil.notBlankAndNull(oper)){
				sbCond.append(" and (t.opercd=:oper or t.opername=:oper)");
				params.put("oper", oper);
			}
		}
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.orderid desc";
		}
		
		sbHql.append(sbCond).append(orderBy);
		sbCountHql.append(sbCond);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取校对订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findCheck(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcROrder t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.orderid) from CmcROrder t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbCond.append(" where ordercat=:ordercat")
			  .append(" and ordertype in(:ordertype)")		//走索引
			  .append(" and dealprogress=:dealprogress")
			  .append(" and checkstatus is not null")
			  ;
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);
		params.put("ordertype", new Integer[]{DictConstant.DICT_ORDERTYPE_TRANSPIC, DictConstant.DICT_ORDERTYPE_TRANSTEXT, DictConstant.DICT_ORDERTYPE_TRANSVIDEO});
		params.put("dealprogress", DictConstants.DICT_DEALPROGRESS_DEALT);
		
				
		String orderBy = null;
		List<Integer> checkStatusList = new ArrayList<Integer>();
		if(map!=null && !map.isEmpty()){
			//订单类型
			String ordertype = map.get("ordertype");
			if(NumberUtil.isNumber(ordertype)){
				sbCond.append(" and t.ordertype=:ordertype");
				params.put("ordertype", Integer.valueOf(ordertype));
			}
			
			//语言
			String lang = map.get("lang");
			if(StringUtil.notBlankAndNull(lang)){
				sbCond.append(" and (t.fromlang=:lang or t.tolang=:lang)");
				params.put("lang", lang);
			}
			
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}

			//译员
			String oper = map.get("oper");
			if(StringUtil.notBlankAndNull(oper)){
				sbCond.append(" and (t.opercd=:oper or t.opername=:oper)");
				params.put("oper", oper);
			}
			
			//核对状态
			String checkstatus = map.get("checkstatus");
			if(StringUtil.notBlankAndNull(checkstatus)){
				String[] arrCheckStatus = StringUtil.split(checkstatus, ",");
				for (String cs : arrCheckStatus) {
					checkStatusList.add(Integer.valueOf(cs));
				}
				if(checkStatusList.size()==1){
					sbCond.append(" and t.checkstatus=:checkstatus");
					params.put("checkstatus", checkStatusList.get(0));
				}else if(checkStatusList.size()>1){
					sbCond.append(" and t.checkstatus in (:checkstatus)");
					params.put("checkstatus", checkStatusList);
				}
			}
			
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
		}
		
		
		if(checkStatusList.isEmpty()){
			orderBy = " order by t.finishtime desc";
		}else if(checkStatusList.contains(DictConstants.DICT_CHECKSTATUS_CHECKED)
					|| checkStatusList.contains(DictConstants.DICT_CHECKSTATUS_REVISED)){
			orderBy = " order by t.checkfinishtime desc";
		}else {
			orderBy = " order by t.finishtime";
		}		
		sbHql.append(sbCond).append(orderBy);
		sbCountHql.append(sbCond);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取翻译处理订单列表，带用户套餐
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findTransDeal(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcROrder t left join fetch t.cmcUCombo");
		StringBuilder sbCountHql = new StringBuilder("select count(t.orderid) from CmcROrder t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbCond.append(" where ordercat=:ordercat");
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);
		
		//处理进度
		String dealprogress = map.get("dealProgress");
		if(StringUtil.notBlankAndNull(dealprogress)){
			String[] arrDealProgress = StringUtil.split(dealprogress, ",");
			if(arrDealProgress.length==1){
				sbCond.append(" and t.dealprogress=:dealprogress");
				params.put("dealprogress", arrDealProgress[0]);
			}else if(arrDealProgress.length>1){
				sbCond.append(" and t.dealprogress in (:dealprogress)");
				params.put("dealprogress", arrDealProgress);
			}
		}else{
			sbCond.append(" and t.dealprogress is not null and t.dealprogress!=''");
		}
		
		if(map!=null && !map.isEmpty()){
			//语言
			String lang = map.get("lang");
			if(StringUtil.notBlankAndNull(lang)){
				sbCond.append(" and (t.fromlang=:lang or t.tolang=:lang)");
				params.put("lang", lang);
			}
			
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}

			//译员
			String oper = map.get("oper");
			if(StringUtil.notBlankAndNull(oper)){
				sbCond.append(" and (t.opercd=:oper or t.opername=:oper)");
				params.put("oper", oper);
			}

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
			String ordertype = map.get("ordertype");
			if(NumberUtil.isNumber(ordertype)){
				sbCond.append(" and t.ordertype=:ordertype");
				params.put("ordertype", Integer.valueOf(ordertype));
			}
			
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(StringUtil.notBlankAndNull(dealstatus)){
				sbCond.append(" and t.dealstatus=:dealstatus");
				params.put("dealstatus", dealstatus);
			}
			
			//评价状态
			String evalstatus = map.get("evalstatus");
			if(NumberUtil.isNumber(evalstatus)){
				sbCond.append(" and t.evalstatus=:evalstatus");
				params.put("evalstatus", Integer.valueOf(evalstatus));
			}
			
			//处理时间
			String begin = map.get("begin");
			if(StringUtil.notBlankAndNull(begin)){
				sbCond.append(" and t.opertime>=:begin");
				params.put("begin", DateUtil.parseDate(begin));
			}
			String end = map.get("end");
			if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.opertime<=:end");
				params.put("end", DateUtil.setMaxTime(DateUtil.parseDate(end)));
			}
		}
		
		String orderBy = null;
		if(StringUtil.isBlankOrNull(dealprogress)){
			orderBy = " order by t.finishtime desc";
		}else if(DictConstants.DICT_DEALPROGRESS_DEALT.equals(dealprogress)){
			orderBy = " order by t.orderid desc";
		}else{
			orderBy = " order by t.waittime";	
		}
		
		sbHql.append(sbCond).append(orderBy);
		sbCountHql.append(sbCond);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	
	/**
	 * 分页获取订单列表
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrderListBean> findOrderInfo(Long userId, String employeeId, Integer orderType, int pageSize, String lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.OrderListBean(")
			 .append("t.orderid, t.userid, t.ordertype, t.fromlang, t.tolang")
			 .append(", t.amount, t.createtime, t.needtime, t.dealstatus, t.evalstatus")
			 .append(", t.appointflag, t.appointtime")
			 .append(") from CmcROrder t")
			 .append(" where t.userid=:userid and t.status=1")
			 ;
		params.put("userid", userId);
		
		if(StringUtil.notBlankAndNull(employeeId)){
			sbHql.append(" and t.employeeid=:employeeid");
			params.put("employeeid", employeeId);
		}
		
		if(orderType!=null){
			sbHql.append(" and t.ordertype=:ordertype");
			params.put("ordertype", orderType);
		}
		
		if(StringUtil.notBlankAndNull(lastId)){
			sbHql.append(" and t.orderid<:orderid");
			params.put("orderid", lastId);
		}
				
		sbHql.append(" order by t.orderid desc");
		
		return (List<OrderListBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取需要退款的订单编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findNeedRefund(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select orderid from CmcROrder t where t.payamount>0")
			 .append(" and t.dealstatus=:dealstatus")
			 .append(" and exists(from CmcRLog l where l.orderid=t.orderid and l.dealstatus=:dealstatus and l.opertime<:opertime)")
		;
		params.put("dealstatus", DictConstant.DICT_ORDER_DEALSTATUS_CANCEL);
		params.put("opertime", DateUtil.addMinute(new Date(), -5));
		
		return (List<String>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 分页获取需取消订单编号
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findNeedCancel(int pageSize, String lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select orderid from CmcROrder where dealstatus=:dealstatus and createtime<:createtime and pickstatus<2");
		params.put("dealstatus", DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY);
		params.put("createtime", DateUtil.addDay(new Date(), -1));		
		
		if(StringUtil.notBlankAndNull(lastId)){
			sbHql.append(" and orderid<:orderid");
			params.put("orderid", lastId);
		}
				
		sbHql.append(" order by orderid desc");
		
		return (List<String>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取待处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcROrder> findWaitDeal(Integer orderCat, List<Integer> orderTypes){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcROrder where ordercat=:ordercat");
		params.put("ordercat", orderCat);
		
		if(orderTypes.size()==1){
			sbHql.append(" and ordertype=:ordertype");
			params.put("ordertype", orderTypes.get(0));
		}else{
			sbHql.append(" and ordertype in (:ordertypes)");
			params.put("ordertypes", orderTypes);
		}
		
		sbHql.append(" and dealprogress=:dealprogress");
		params.put("dealprogress", DictConstants.DICT_DEALPROGRESS_WAITING);
		
		sbHql.append(" and waittime<now()")
			 .append(" order by orderid desc")
			 ;
		
		return (List<CmcROrder>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取即将需要处理的预约订单，带用户套餐
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcROrder> findSoonDeal(Integer orderCat, List<Integer> orderTypes){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcROrder t left join fetch t.cmcUCombo where t.ordercat=:ordercat");
		params.put("ordercat", orderCat);
		
		if(orderTypes.size()==1){
			sbHql.append(" and t.ordertype=:ordertype");
			params.put("ordertype", orderTypes.get(0));
		}else{
			sbHql.append(" and t.ordertype in (:ordertypes)");
			params.put("ordertypes", orderTypes);
		}
		
		sbHql.append(" and t.dealprogress=:dealprogress");
		params.put("dealprogress", DictConstants.DICT_DEALPROGRESS_WAITING);
		
		sbHql.append(" and t.waittime between now() and :endTime")
			 .append(" and t.soondealstatus=:soondealstatus")
			 .append(" order by t.waittime")
			 ;
		params.put("soondealstatus", SystemConstants.STATUS_OPER_ENABLED);
		params.put("endTime", DateUtil.addMinute(new Date(), 2));
		
		return (List<CmcROrder>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取未完成处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcROrder> findUndealt(Integer orderCat, List<Integer> orderTypes){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcROrder where ordercat=:ordercat");
		params.put("ordercat", orderCat);
		
		if(orderTypes.size()==1){
			sbHql.append(" and ordertype=:ordertype");
			params.put("ordertype", orderTypes.get(0));
		}else{
			sbHql.append(" and ordertype in (:ordertypes)");
			params.put("ordertypes", orderTypes);
		}
		
		sbHql.append(" and dealprogress in(:dps)");
		params.put("dps", new String[]{DictConstants.DICT_DEALPROGRESS_PROCESSING, DictConstants.DICT_DEALPROGRESS_WAITING});

		sbHql.append(" and waittime<now()")
			 .append(" order by waittime")
			 ;
		
		return (List<CmcROrder>)qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取评价列表
	 * @param orderIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcREval> findEval(List<String> orderIds){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcREval where orderid in(:orderIds)";
		params.put("orderIds", orderIds);
		
		return (List<CmcREval>) qryListByMap(hql, params);
	}
	
	/**
	 * 订单日统计
	 * @param orderCat not null
	 * @param dealStatus
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrderDayCountBean> findOrderDayCount(Integer orderCat, String dealStatus, Date start, Date end){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.vo.OrderDayCountBean(")
			 .append("DATE_FORMAT(createtime, '%Y-%m-%d'), count(orderid)")
			 .append(") from CmcROrder where ordercat=:ordercat")
			 .append(" and createtime between :start and :end")			 
		;
		params.put("ordercat", orderCat);
		params.put("start", start);
		params.put("end", end);
		
		if(StringUtil.notBlankAndNull(dealStatus)){
			sbHql.append(" and dealstatus=:dealstatus");
			params.put("dealstatus", dealStatus);
		}
		
		sbHql.append(" group by DATE_FORMAT(createtime, '%Y-%m-%d')");
		sbHql.append(" order by 1 desc");
		
		return (List<OrderDayCountBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 订单统计
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrderCountBean> findOrderCount(Date start, Date end){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.vo.OrderCountBean(")
			 .append("ordertype, dealstatus, count(orderid), sum(amount), sum(couponamount), sum(walletamount), sum(payamount)")
			 .append(") from CmcROrder")
			 .append(" where createtime between :start and :end")			 
		;
		params.put("start", start);
		params.put("end", end);
		
		sbHql.append(" group by ordertype, dealstatus");
		
		return (List<OrderCountBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取订单信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcROrder getOrder(String orderId, Long userId){
		String hql = "from CmcROrder where orderid=:orderid and userid=:userid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderId);
		params.put("userid", userId);
		
		return (CmcROrder) load(hql, params);
	}

	/**
	 * 获取订单评价
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmcREval getOrderEval(String orderId){
		List<CmcREval> evals = (List<CmcREval>)qryList("from CmcREval where orderid=?", orderId);
		if(!evals.isEmpty()){
			return evals.get(0);
		}
		return null;
	}
	
	/**
	 * 获取用户指定日期订单数
	 * @param userId
	 * @param date
	 * @param orderType
	 * @return
	 */
	public Long getUserDayCount(Long userId, Date date, Integer orderType){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(orderid) from CmcROrder where userid=:userid")
			 .append(" and ordertype=:ordertype")
			 .append(" and createtime between :start and :end")
			 ;
		
		params.put("userid", userId);
		params.put("ordertype", orderType);
		params.put("start", DateUtil.setMinTime(date));
		params.put("end", DateUtil.setMaxTime(date));
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户处理（待处理、处理中、已处理）订单数
	 * @param userId not null
	 * @param orderType
	 * @param dealProgress
	 * @return
	 */
	public Long getUserProcessCount(Long userId, Integer orderType, String dealProgress){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(orderid) from CmcROrder where userid=:userid");
		params.put("userid", userId);
		
		if(orderType!=null){
			sbHql.append(" and ordertype=:ordertype");
			params.put("ordertype", orderType);
		}
		
		if(StringUtil.notBlankAndNull(dealProgress)){
			sbHql.append(" and dealprogress=:dealprogress");
			params.put("dealprogress", dealProgress);	
		}else{
			sbHql.append(" and (dealprogress is not null or dealprogress!='')");
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取处理订单数
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @param dealprogressList not null
	 * @return
	 */
	public Long getProcessCount(Integer orderCat, List<Integer> orderTypes, List<String> dealprogressList){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(orderid) from CmcROrder where ordercat=:ordercat");
		params.put("ordercat", orderCat);
		
		if(orderTypes.size()==1){
			sbHql.append(" and ordertype=:ordertype");
			params.put("ordertype", orderTypes.get(0));
		}else{
			sbHql.append(" and ordertype in (:ordertypes)");
			params.put("ordertypes", orderTypes);
		}
		
		if(dealprogressList.size()==1){
			sbHql.append(" and dealprogress=:dealprogress");
			params.put("dealprogress", dealprogressList.get(0));
		}else{
			sbHql.append(" and dealprogress in (:dealprogress)");
			params.put("dealprogress", dealprogressList);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取新的订单编号
	 * @return
	 * 修改历史：
	 */
	public String newOrderId(){
		BigInteger nextSeq = (BigInteger) getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select nextval('SEQ_CMC_ORDER_ID')").uniqueResult();
			}
		});

		String seq = StringUtil.right(String.valueOf(nextSeq.longValue()), 6);
		
		return new StringBuilder()
			.append(DateUtil.format(new Date(), "yyMMddHHmmss"))
			.append(seq)
			.toString()
			;
	}
	
	/**
	 * 更新状态
	 * @param orderId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String orderId, Integer status){
		update("update CmcROrder set status=? where orderid=?", new Object[]{status, orderId});
	}
	
	/**
	 * 更新处理状态
	 * @param orderId
	 * @param dealStatus
	 * 修改历史：
	 */
	public void updateDealStatus(String orderId, String dealStatus){
		update("update CmcROrder set dealstatus=? where orderid=?", new Object[]{dealStatus, orderId});
	}
	
	/**
	 * 更新处理进度
	 * @param orderId
	 * @param dealProgress
	 * 修改历史：
	 */
	public void updateDealProgress(String orderId, String dealProgress){
		update("update CmcROrder set dealprogress=? where orderid=?", new Object[]{dealProgress, orderId});
	}
	
	/**
	 * 更新地址
	 * @param orderId
	 * @param address
	 */
	public void updateAddress(String orderId, String address){
		update("update CmcROrder set address=? where orderid=?", new Object[]{address, orderId});
	}
	
	/**
	 * 更新是否修正过
	 * @param orderId
	 * @param revise
	 */
	public void updateRevise(String orderId, Integer revise){
		update("update CmcROrder set revise=? where orderid=?", new Object[]{revise, orderId});
	}
	
	/**
	 * 保存完成校对结果
	 * @param orderId
	 * @param revise
	 * @param checkStatus
	 */
	public void saveFinishCheck(String orderId, Integer revise, Integer checkStatus){
		String hql = "update CmcROrder set revise=?, checkstatus=?, checkfinishtime=now() where orderid=?";
		update(hql, new Object[]{revise, checkStatus, orderId});
	}
	
	/**
	 * 更新评价状态
	 * @param orderId
	 * @param evalStatus
	 */
	public void updateEvalStatus(String orderId, Integer evalStatus){
		update("update CmcROrder set evalstatus=? where orderid=?", new Object[]{evalStatus, orderId});
	}
	
	/**
	 * 更新佣金结算结果
	 * @param orderId
	 * @param commissionStatus
	 * @param commissionAmount
	 * @param commissionDate
	 */
	public void updateCommission(String orderId, Integer commissionStatus, Double commissionAmount, Date commissionDate){
		update("update CmcROrder set commissionstatus=?, commissionamount=?, commissiondate=? where orderid=?", new Object[]{commissionStatus, commissionAmount, commissionDate, orderId});
	}
	
	/**
	 * 更新即将开始处理状态
	 * @param orderId
	 * @param soonDealStatus
	 */
	public void updateSoonDealStatus(String orderId, Integer soonDealStatus){
		update("update CmcROrder set soondealstatus=? where orderid=?", new Object[]{soonDealStatus, orderId});
	}
}
