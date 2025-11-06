package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransVideoDao;
import com.qcmz.cmc.entity.CmcVtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealListBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class TransVideoDao extends BaseDAO implements ITransVideoDao {
	/**
	 * 分页获取视频口译信息，含订单信息
	 * @param map
	 * @param pageBean<CmcVtTrans>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcVtTrans t left join fetch t.cmcROrder");
		StringBuilder sbCountHql = new StringBuilder("select count(t.vtid) from CmcVtTrans t");
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
			
			//处理人
			String oper = map.get("oper");
			if(StringUtil.notBlankAndNull(oper)){
				sbCond.append(" and (t.cmcROrder.opercd=:oper or t.cmcROrder.opername=:oper)");
				params.put("oper", oper);
			}
			
			//是否用券
			String isUseCoupon = map.get("isUseCoupon");
			if(StringUtil.notBlankAndNull(isUseCoupon)){
				boolean use = Boolean.valueOf(isUseCoupon);
				if(use){
					sbCond.append(" and t.cmcROrder.couponamount>0");
				}else{
					sbCond.append(" and t.cmcROrder.couponamount=0");
				}
			}
			
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(StringUtil.notBlankAndNull(dealstatus)){
				sbCond.append(" and t.cmcROrder.dealstatus=:dealstatus");
				params.put("dealstatus", dealstatus);
			}
			
			//处理进度
			String dealProgress = map.get("dealProgress");
			if(StringUtil.notBlankAndNull(dealProgress)){
				sbCond.append(" and t.cmcROrder.dealprogress=:dealProgress");
				params.put("dealProgress", dealProgress);
				if(DictConstants.DICT_DEALPROGRESS_WAITING.equals(dealProgress)
						|| DictConstants.DICT_DEALPROGRESS_PROCESSING.equals(dealProgress)){
					orderBy = " order by t.vtid";
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
			
			//提交时间
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
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.vtid desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取视频口译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransVideoDealListBean> findVideo4Deal(OrderDealQueryBean query, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sortIdSql = null;
		if(StringUtil.isBlankOrNull(query.getDealProgress())){
			sortIdSql = "t.orderid";
		}else if(DictConstants.DICT_DEALPROGRESS_DEALT.equals(query.getDealProgress())){
			sortIdSql = "CONCAT(unix_timestamp(t.cmcROrder.finishtime), t.orderid)";
		}else{
			sortIdSql = "CONCAT(unix_timestamp(t.cmcROrder.waittime), t.orderid)";
		}
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransVideoDealListBean(")
			 .append(sortIdSql)
			 .append(", t.orderid, t.cmcROrder.ordercat, t.cmcROrder.ordertype, t.userid, t.cmcROrder.amount, t.cmcROrder.couponamount, t.cmcROrder.ucid")
			 .append(", t.cmcROrder.appointflag, t.cmcROrder.lon, t.cmcROrder.lat, t.cmcROrder.address, t.cmcROrder.requirement")
			 .append(", t.cmcROrder.createtime, t.cmcROrder.waittime, t.cmcROrder.opertime, t.cmcROrder.needtime, t.cmcROrder.finishtime")
			 .append(", t.cmcROrder.opercd, t.cmcROrder.opername, t.cmcROrder.dealstatus, t.cmcROrder.dealprogress")
			 .append(", t.cmcROrder.checkcd, t.cmcROrder.checkname, t.cmcROrder.checkstatus, t.cmcROrder.evalstatus")
			 .append(", t.cmcROrder.fromlang, t.cmcROrder.tolang, t.cmcROrder.commissionamount, t.cmcROrder.commissionstatus, t.cmcROrder.platform, t.cmcROrder.version")
			 .append(", t.sceneid, t.roomid, t.calltype, a.balance")
			 .append(") from CmcVtTrans t left join t.cmcWalletAccount a")
			 .append(" where t.cmcROrder.ordercat=:ordercat")
			 .append(" and t.cmcROrder.ordertype=:ordertype")
		;
		
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);		//便于走索引
		params.put("ordertype", DictConstant.DICT_ORDERTYPE_TRANSVIDEO);	//便于走索引
		
		StringBuilder sbCond = new StringBuilder();
		String orderBy = " order by 1";
		
		//处理进度
		if(StringUtil.notBlankAndNull(query.getDealProgress())){
			sbCond.append(" and t.cmcROrder.dealprogress=:dealprogress");
			params.put("dealprogress", query.getDealProgress());
		}else{
			sbCond.append(" and t.cmcROrder.dealprogress is not null");
		}
		
		if(query.getUid()!=null){
			sbCond.append(" and t.userid=:userid");
			params.put("userid", query.getUid());
		}
		
		if(query.getLangs()!=null && !query.getLangs().isEmpty()){
			sbHql.append(" and (t.fromlang in (:langs) or t.tolang in(:langs))");
			params.put("langs", query.getLangs());
		}
		
		if(StringUtil.notBlankAndNull(query.getOperator())){
			sbCond.append(" and t.cmcROrder.opercd=:opercd");
			params.put("opercd", query.getOperator());
		}
		
		if(StringUtil.isBlankOrNull(query.getDealProgress())
				|| DictConstants.DICT_DEALPROGRESS_DEALT.equals(query.getDealProgress())){
			//已完成倒序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbCond.append(" and ").append(sortIdSql).append(" <:baseid");
				params.put("baseid", query.getMoreBaseId());
			}
			orderBy = " order by 1 desc";
		}else{
			//正序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbCond.append(" and ").append(sortIdSql).append(" >:baseid");
				params.put("baseid", query.getMoreBaseId());
			}
		}
		
		sbHql.append(sbCond).append(orderBy);
		
		return (List<TransVideoDealListBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取视频口译列表
	 * @param orderIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcVtTrans> findVideo(List<String> orderIds){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcVtTrans where orderid in(:orderIds)";
		params.put("orderIds", orderIds);
		
		return (List<CmcVtTrans>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取视频口译信息
	 * @param orderId
	 * @return
	 */
	public CmcVtTrans getTrans(String orderId){
		return (CmcVtTrans) load("from CmcVtTrans where orderid=?", orderId);
	}
	
	/**
	 * 更新接通时间
	 * @param orderId
	 * 修改历史：
	 */
	public void updateConnectTime(String orderId){
		update("update CmcVtTrans set connecttime=now() where orderid=?", orderId);
	}
	
	/**
	 * 更新开始计费时间
	 * @param orderId
	 * 修改历史：
	 */
	public void updateStartBillingTime(String orderId){
		update("update CmcVtTrans set startbillingtime=now() where orderid=?", orderId);
	}
	
	/**
	 * 更新接通和开始计费时间
	 * @param orderId
	 * 修改历史：
	 */
	public void updateConnectAndStartBillingTime(String orderId){
		update("update CmcVtTrans set connecttime=now(), startbillingtime=now() where orderid=?", orderId);
	}
}
