package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransPicDao;
import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransPicBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealListBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：图片翻译
 * 修改历史：
 */
@Repository
public class TransPicDao extends BaseDAO implements ITransPicDao {
	
	/**
	 * 分页获取图片翻译信息，含订单信息
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcPtPic t left join fetch t.cmcROrder");
		StringBuilder sbCountHql = new StringBuilder("select count(t.picid) from CmcPtPic t");
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
			
			//翻译途径
			String transway = map.get("transway");
			if(StringUtil.notBlankAndNull(transway)){
				sbCond.append(" and t.transway=:transway");
				params.put("transway", transway);
			}
			String exceptTransway = map.get("exceptTransway");
			if(StringUtil.notBlankAndNull(exceptTransway)){
				sbCond.append(" and t.transway!=:exceptTransway");
				params.put("exceptTransway", exceptTransway);
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
					orderBy = " order by t.picid";
				}
			}
			
			//图片编号
			String picid = map.get("picid");
			if(StringUtil.notBlankAndNull(picid)){
				if(picid.length()==18){
					sbCond.append(" and t.picid = :picid");
					params.put("picid", picid);
				}else{
					sbCond.append(" and t.picid like :picid");
					params.put("picid", "%"+picid);
				}
			}
			
			//提交时间
			String begin = map.get("begin");
			if(StringUtil.notBlankAndNull(begin)){
				sbCond.append(" and t.cmcROrder.createtime>=:begin");
				params.put("begin", DateUtil.parseDate(begin));
			}
			String end = map.get("end");
			String endTime = map.get("endtime");
			if(StringUtil.notBlankAndNull(endTime)){
				sbCond.append(" and t.cmcROrder.createtime<=:endTime");
				params.put("endTime", DateUtil.parseDateTime(endTime));
			}else if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.cmcROrder.createtime<=:end");
				params.put("end", DateUtil.setMaxTime(DateUtil.parseDate(end)));
			}
			
			
			//下单平台
			String platform = map.get("platform");
			if(StringUtil.notBlankAndNull(platform)){
				sbCond.append(" and t.cmcROrder.platform=:platform");
				params.put("platform", platform);
			}
			
			//业务类型
			String servicetype = map.get("servicetype");
			if(StringUtil.notBlankAndNull(servicetype)){
				sbCond.append(" and t.cmcROrder.servicetype=:servicetype");
				params.put("servicetype", servicetype);
			}
			
			String pickstatus = map.get("pickstatus");
			if(NumberUtil.isNumber(pickstatus)){
				sbCond.append(" and t.cmcROrder.pickstatus=:pickstatus");
				params.put("pickstatus", Integer.valueOf(pickstatus));
			}else if(StringUtil.notBlankAndNull(pickstatus)){
				String[] arr = StringUtil.split(pickstatus, ",");
				List<Integer> pickstatuses = new ArrayList<Integer>();
				for (String str : arr) {
					pickstatuses.add(Integer.valueOf(str));
				}
				sbCond.append(" and t.cmcROrder.pickstatus in(:pickstatuses)");
				params.put("pickstatuses", pickstatuses);
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
			}
			
		}
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.picid desc";
		}
		
		sbHql.append(sbCond).append(orderBy);
		sbCountHql.append(sbCond);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取用户图片翻译列表
	 * @param userId
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransPicBean> findPic(Long userId, int pageSize, String lastPicId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransPicBean(")
			 .append("t.picid, t.cmcROrder.userid, t.transway, t.fromlang, t.tolang, t.picurl, t.thumburl, t.cmcROrder.createtime, t.cmcROrder.address, t.cmcROrder.amount, t.cmcROrder.dealstatus")
			 .append(") from CmcPtPic t where t.userid=:userid and t.cmcROrder.status=1")
		;
		params.put("userid", userId);
		
		if(StringUtil.notBlankAndNull(lastPicId)){
			sbHql.append(" and t.picid<:picid");
			params.put("picid", lastPicId);
		}
				
		sbHql.append(" order by t.picid desc");
		
		return (List<TransPicBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 分页获取图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransPicDealListBean> findPic4Deal(OrderDealQueryBean query, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransPicDealListBean(")
			 .append("t.orderid, t.orderid, t.cmcROrder.ordercat, t.cmcROrder.ordertype, t.userid, t.cmcROrder.amount, t.cmcROrder.couponamount, t.cmcROrder.ucid")
			 .append(", t.cmcROrder.appointflag, t.cmcROrder.lon, t.cmcROrder.lat, t.cmcROrder.address, t.cmcROrder.requirement")
			 .append(", t.cmcROrder.createtime, t.cmcROrder.waittime, t.cmcROrder.opertime, t.cmcROrder.needtime, t.cmcROrder.finishtime")
			 .append(", t.cmcROrder.opercd, t.cmcROrder.opername, t.cmcROrder.dealstatus, t.cmcROrder.dealprogress")
			 .append(", t.cmcROrder.checkcd, t.cmcROrder.checkname, t.cmcROrder.checkstatus, t.cmcROrder.evalstatus")
			 .append(", t.cmcROrder.fromlang, t.cmcROrder.tolang, t.cmcROrder.commissionamount, t.cmcROrder.commissionstatus, t.cmcROrder.platform, t.cmcROrder.version")
			 .append(", t.transway, t.picurl, t.thumburl")
			 .append(") from CmcPtPic t")
			 .append(" where t.cmcROrder.ordercat=:ordercat")
			 .append(" and t.cmcROrder.ordertype=:ordertype")
		;
		
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);		//便于走索引
		params.put("ordertype", DictConstant.DICT_ORDERTYPE_TRANSPIC);	//便于走索引
		
		//处理进度
		if(StringUtil.notBlankAndNull(query.getDealProgress())){
			sbHql.append(" and t.cmcROrder.dealprogress=:dealprogress");
			params.put("dealprogress", query.getDealProgress());		
		}else{
			sbHql.append(" and t.cmcROrder.dealprogress is not null");
		}
		
		if(StringUtil.notBlankAndNull(query.getOperator())){
			sbHql.append(" and t.cmcROrder.opercd=:opercd");
			params.put("opercd", query.getOperator());
		}
		
		if(query.getUid()!=null){
			sbHql.append(" and t.userid=:userid");
			params.put("userid", query.getUid());
		}
		
		if(query.getLangs()!=null && !query.getLangs().isEmpty()){
			sbHql.append(" and (t.cmcROrder.fromlang in (:langs) or t.cmcROrder.tolang in(:langs))");
			params.put("langs", query.getLangs());
		}
		
		if(StringUtil.isBlankOrNull(query.getDealProgress())
				|| DictConstants.DICT_DEALPROGRESS_DEALT.equals(query.getDealProgress())){
			//已完成倒序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbHql.append(" and t.orderid<:orderid");
				params.put("orderid", query.getMoreBaseId());
			}
			
			sbHql.append(" order by t.orderid desc");
		}else{
			//正序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbHql.append(" and t.orderid>:orderid");
				params.put("orderid", query.getMoreBaseId());
			}
					
			sbHql.append(" order by t.orderid");
		}
		
		return (List<TransPicDealListBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 分页获取捡漏图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransPicDealListBean> findPic4PickDeal(OrderDealQueryBean query, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sortIdHql = null;
		if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(query.getDealStatus())){
			sortIdHql = "CONCAT(unix_timestamp(t.cmcROrder.createtime), t.orderid)";
		}else{
			sortIdHql = "CONCAT(unix_timestamp(t.cmcROrder.opertime), t.orderid)";
		}
				
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.TransPicDealListBean(")
			 .append(sortIdHql).append(", t.orderid, t.cmcROrder.ordercat, t.cmcROrder.ordertype, t.userid, t.cmcROrder.amount, t.cmcROrder.couponamount, t.cmcROrder.ucid")
			 .append(", t.cmcROrder.appointflag, t.cmcROrder.lon, t.cmcROrder.lat, t.cmcROrder.address, t.cmcROrder.requirement")
			 .append(", t.cmcROrder.createtime, t.cmcROrder.waittime, t.cmcROrder.opertime, t.cmcROrder.needtime, t.cmcROrder.finishtime")
			 .append(", t.cmcROrder.opercd, t.cmcROrder.opername, t.cmcROrder.dealstatus, t.cmcROrder.dealprogress")
			 .append(", t.cmcROrder.checkcd, t.cmcROrder.checkname, t.cmcROrder.checkstatus, t.cmcROrder.evalstatus")
			 .append(", t.cmcROrder.fromlang, t.cmcROrder.tolang, t.cmcROrder.commissionamount, t.cmcROrder.commissionstatus, t.cmcROrder.platform, t.cmcROrder.version")
			 .append(", t.transway, t.picurl, t.thumburl")
			 .append(") from CmcPtPic t")
			 .append(" where t.cmcROrder.ordercat=:ordercat")
			 .append(" and t.cmcROrder.ordertype=:ordertype")
			 .append(" and t.cmcROrder.pickstatus>0 and t.cmcROrder.createtime<=:createtime")
		;
		
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);		//便于走索引
		params.put("ordertype", DictConstant.DICT_ORDERTYPE_TRANSPIC);	//便于走索引
		params.put("createtime", DateUtil.addMinute(new Date(), -SystemConfig.ORDERPICK_AFTERTIME));
		
		if(StringUtil.notBlankAndNull(query.getDealStatus())){
			sbHql.append(" and t.cmcROrder.dealstatus=:dealstatus");
			params.put("dealstatus", query.getDealStatus());		
		}
		
		if(StringUtil.notBlankAndNull(query.getOperator())){
			sbHql.append(" and t.cmcROrder.opercd=:opercd");
			params.put("opercd", query.getOperator());
		}
		
		if(query.getLangs()!=null && !query.getLangs().isEmpty()){
			sbHql.append(" and (t.fromlang in (:langs) or t.tolang in(:langs))");
			params.put("langs", query.getLangs());
		}
		
		if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
			sbHql.append(" and ").append(sortIdHql).append(" <:baseid");
			params.put("baseid", query.getMoreBaseId());
		}
		sbHql.append(" order by 1 desc");
		
		List<TransPicDealListBean> result = (List<TransPicDealListBean>) qryListTop(sbHql.toString(), params, pageSize);
		return result;
	}
	
	/**
	 * 获取图片列表
	 * @param picIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPtPic> findPic(List<String> orderIds){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "from CmcPtPic where orderid in(:orderids)";
		params.put("orderids", orderIds);
		
		return (List<CmcPtPic>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取图片译文列表
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPtTrans> findTrans(String orderId){
		return (List<CmcPtTrans>) qryList("from CmcPtTrans where picid=? order by transid", orderId);
	}
	
	/**
	 * 获取译文列表
	 * @param orderIds
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPtTrans> findTrans(List<String> orderIds){
		if(orderIds==null || orderIds.isEmpty()) return new ArrayList<CmcPtTrans>();
		if(orderIds.size()==1){
			return findTrans(orderIds.get(0));
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("picids", orderIds);
		
		return (List<CmcPtTrans>) qryListByMap("from CmcPtTrans where picid in(:picids) order by transid", params);
	}
	
	/**
	 * 更新图片地址
	 * @param picId
	 * @param picUrl
	 * @param thumbUrl
	 * 修改历史：
	 */
	public void updatePicUrl(String picId, String picUrl, String thumbUrl){
		update("update CmcPtPic set picurl=?,thumburl=? where picid=?", new Object[]{picUrl, thumbUrl, picId});
	}
	
	/**
	 * 更新地址
	 * @param picId
	 * @param address
	 */
	public void updateAddress(String picId, String address){
		update("update CmcPtPic set address=? where picid=?", new Object[]{address, picId});
	}

	/**
	 * 清除图片翻译结果
	 * @param picId
	 * 修改历史：
	 */
	public void clearTrans(String picId){
		delete("delete from CmcPtTrans where picid=?", picId);
	}
	
	/**
	 * 删除不保留的翻译结果
	 * @param picId
	 * @param retainTransIds 保留的翻译编号列表
	 * 修改历史：
	 */
	public void deleteTransUnRetain(String picId, List<Long> retainTransIds){
		if(retainTransIds==null || retainTransIds.isEmpty()){
			clearTrans(picId);
		}else{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("picid", picId);
			params.put("ids", retainTransIds);
			deleteByMap("delete from CmcPtTrans where picid=:picid and transid not in(:ids)", params);
		}
	}
	
}
