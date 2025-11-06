package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransComboOrderDao;
import com.qcmz.cmc.entity.CmcRCombo;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class TransComboOrderDao extends BaseDAO implements ITransComboOrderDao {
	/**
	 * 分页获取翻译套餐，含订单信息
	 * @param map
	 * @param pageBean<CmcRCombo>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcRCombo t left join fetch t.cmcROrder");
		StringBuilder sbCountHql = new StringBuilder("select count(t.id) from CmcRCombo t");
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
			
			//套餐名称
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
	 * 获取用户已购买套餐订单数
	 * @param userId
	 * @param pkgId
	 * @return
	 */
	public Long getBoughtCount(Long userId, Long pkgId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.orderid) from CmcRCombo t")
			 .append(" where t.cmcROrder.userid=:userid")
			 .append(" and t.pkgid=:pkgid")
			 .append(" and t.cmcROrder.dealstatus in(:dealstatus)")
			 ;
		params.put("userid", userId);
		params.put("pkgid", pkgId);
		
		List<String> dealstatuses = new ArrayList<String>();
		dealstatuses.add(DictConstant.DICT_ORDER_DEALSTATUS_PAID);
		dealstatuses.add(DictConstant.DICT_ORDER_DEALSTATUS_WAITDEAL);
		dealstatuses.add(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		params.put("dealstatus", dealstatuses);
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取翻译订单套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcRCombo getCombo(String orderId){
		return (CmcRCombo) load("from CmcRCombo where orderid=?", orderId);
	}
}
