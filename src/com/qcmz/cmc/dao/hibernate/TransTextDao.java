package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransTextDao;
import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransTextTransBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class TransTextDao extends BaseDAO implements ITransTextDao {
	/**
	 * 分页获取短文快译信息，含订单信息
	 * @param map
	 * @param pageBean<CmcTtTrans>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcTtTrans t left join fetch t.cmcROrder");
		StringBuilder sbCountHql = new StringBuilder("select count(t.ttid) from CmcTtTrans t");
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
					orderBy = " order by t.ttid";
				}
			}
			
			//翻译模式
			String transmodel = map.get("transmodel");
			if(NumberUtil.isNumber(transmodel)){
				sbCond.append(" and t.transmodel=:transmodel");
				params.put("transmodel", Integer.valueOf(transmodel));
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
			orderBy = " order by t.ttid desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取短文快译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransTextDealListBean> findText4Deal(OrderDealQueryBean query, int pageSize){
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
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransTextDealListBean(")
			 .append(sortIdSql)
			 .append(", t.orderid, t.cmcROrder.ordercat, t.cmcROrder.ordertype, t.userid, t.cmcROrder.amount, t.cmcROrder.couponamount, t.cmcROrder.ucid")
			 .append(", t.cmcROrder.appointflag, t.cmcROrder.lon, t.cmcROrder.lat, t.cmcROrder.address, t.cmcROrder.requirement")
			 .append(", t.cmcROrder.createtime, t.cmcROrder.waittime, t.cmcROrder.opertime, t.cmcROrder.needtime, t.cmcROrder.finishtime")
			 .append(", t.cmcROrder.opercd, t.cmcROrder.opername, t.cmcROrder.dealstatus, t.cmcROrder.dealprogress")
			 .append(", t.cmcROrder.checkcd, t.cmcROrder.checkname, t.cmcROrder.checkstatus, t.cmcROrder.evalstatus")
			 .append(", t.cmcROrder.fromlang, t.cmcROrder.tolang, t.cmcROrder.commissionamount, t.cmcROrder.commissionstatus, t.cmcROrder.platform, t.cmcROrder.version")
			 .append(", t.src, t.dst")
			 .append(") from CmcTtTrans t")
			 .append(" where t.cmcROrder.ordercat=:ordercat")
			 .append(" and t.cmcROrder.ordertype=:ordertype")
			 ;
	
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);		//便于走索引
		params.put("ordertype", DictConstant.DICT_ORDERTYPE_TRANSTEXT);	//便于走索引
		
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
			sbCond.append(" and (t.fromlang in (:langs) or t.tolang in(:langs))");
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
		
		return (List<TransTextDealListBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取用户之前翻译记录
	 * @param userId
	 * @param pageSize
	 * @param baseOrderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransTextTransBean> findUserTransBefore(Long userId, int pageSize, String baseOrderId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransTextTransBean(")
			 .append("t.orderid, t.fromlang, t.tolang, t.src, t.dst")
			 .append(") from CmcTtTrans t")
			 .append(" where t.userid=:userid and t.orderid<:baseid")
			 ;
		params.put("userid", userId);
		params.put("baseid", baseOrderId);
		
		return (List<TransTextTransBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取用户之后翻译记录
	 * @param userId
	 * @param pageSize
	 * @param baseOrderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransTextTransBean> findUserTransAfter(Long userId, int pageSize, String baseOrderId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransTextTransBean(")
			 .append("t.orderid, t.fromlang, t.tolang, t.src, t.dst")
			 .append(") from CmcTtTrans t")
			 .append(" where t.userid=:userid and t.orderid>:baseid")
			 ;
		params.put("userid", userId);
		params.put("baseid", baseOrderId);
		
		return (List<TransTextTransBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取短文快译列表
	 * @param picIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTtTrans> findText(List<String> orderIds){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcTtTrans where orderid in(:orderIds)";
		params.put("orderIds", orderIds);
		
		return (List<CmcTtTrans>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取短文快译信息
	 * @param orderId
	 * @return
	 */
	public CmcTtTrans getTrans(String orderId){
		return (CmcTtTrans) load("from CmcTtTrans where orderid=?", orderId);
	}
	
	/**
	 * 更新语音
	 * @param orderId
	 * @param voice
	 */
	public void updateVoice(String orderId, String voice){
		String hql = null;
		Object[] values = null;
		if(voice!=null){
			hql = "update CmcTtTrans set voice=? where orderid=?";
			values = new Object[]{voice, orderId};
		}else{
			hql = "update CmcTtTrans set voice=null where orderid=?";
			values = new Object[]{orderId};
		}
		update(hql, values);
	}
}
