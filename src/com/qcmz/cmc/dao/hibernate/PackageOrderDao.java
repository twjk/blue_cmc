package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.IPackageOrderDao;
import com.qcmz.cmc.entity.CmcRPackage;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class PackageOrderDao extends BaseDAO implements IPackageOrderDao {
	/**
	 * 分页获取优惠券包，含订单信息
	 * @param map
	 * @param pageBean<CmcRPackage>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcRPackage t left join fetch t.cmcROrder");
		StringBuilder sbCountHql = new StringBuilder("select count(t.pkgid) from CmcRPackage t");
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
			
			//兑换码
			String exchangecode = map.get("exchangecode");
			if(StringUtil.notBlankAndNull(exchangecode)){
				sbCond.append(" and t.exchangecode=:exchangecode");
				params.put("exchangecode", exchangecode);
			}
			
			//兑换状态
			String exchangestatus = map.get("exchangestatus");
			if(NumberUtil.isNumber(exchangestatus)){
				sbCond.append(" and t.exchangestatus=:exchangestatus");
				params.put("exchangestatus", Integer.valueOf(exchangestatus));
			}
			
			//下单平台
			String platform = map.get("platform");
			if(StringUtil.notBlankAndNull(platform)){
				sbCond.append(" and t.cmcROrder.platform=:platform");
				params.put("platform", platform);
			}
			
			//活动名称
			String acttitle = map.get("acttitle");
			if(StringUtil.notBlankAndNull(acttitle)){
				sbCond.append(" and t.acttitle like :acttitle");
				params.put("acttitle", "%"+acttitle+"%");
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.pkgid desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取优惠券包信息
	 * @param orderId
	 * @return
	 */
	public CmcRPackage getPackage(String orderId){
		return (CmcRPackage) load("from CmcRPackage where orderid=?", orderId);
	}
}
