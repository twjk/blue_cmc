package com.qcmz.cmc.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IUserTransComboDao;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.entity.CmcUCombohis;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class UserTransComboDao extends BaseDAO implements IUserTransComboDao {
	/**
	 * 分页获取用户翻译套餐列表，带套餐
	 * @param map
	 * @param pageBean<CmcUCombo>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcUCombo t left join fetch t.cmcCombo");
		StringBuilder sbCountHql = new StringBuilder("select count(t.ucid) from CmcUCombo t");
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
			
			//付款方
			String payside = map.get("payside");
			if(NumberUtil.isNumber(payside)){
				sbCond.append(" and t.payside=:payside");
				params.put("payside", Integer.valueOf(payside));
			}
			
			//服务途径
			String serviceway = map.get("serviceway");
			if(NumberUtil.isNumber(serviceway)){
				sbCond.append(" and t.cmcCombo.serviceway=:serviceway");
				params.put("serviceway", Integer.valueOf(serviceway));
			}
			
			//套餐名称
			String combotitle = map.get("combotitle");
			if(StringUtil.notBlankAndNull(combotitle)){
				sbCond.append(" and t.combotitle like :combotitle");
				params.put("combotitle", "%"+combotitle+"%");
			}
			
			//兑换码
			String exchangecode = map.get("exchangecode");
			if(StringUtil.notBlankAndNull(exchangecode)){
				sbCond.append(" and t.exchangecode=:exchangecode");
				params.put("exchangecode", exchangecode.trim());
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		
		if(StringUtil.isBlankOrNull(orderBy)){
			orderBy = " order by t.ucid desc";
		}
		sbHql.append(orderBy);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取用户有效的套餐列表
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcUCombo> findUserValidCombo(Long userId, String transType, Integer comboType){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sbHql = new StringBuilder();
		
		sbHql.append("from CmcUCombo where userid=:userid")
			 .append(" and (usablenum is null or usablenum>0)")
			 .append(" and (endtime is null or endtime>=now())")
			 ;
		params.put("userid", userId);
		
		if(StringUtil.notBlankAndNull(transType)){
			sbHql.append(" and transtype=:transtype");
			params.put("transtype", transType);
		}
		
		if(comboType!=null){
			sbHql.append(" and combotype=:combotype");
			params.put("combotype", comboType);
		}
		
		sbHql.append(" order by ucid desc");
		
		return (List<CmcUCombo>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户套餐列表
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcUCombo> findUserCombo(Long userId, String transType, Integer comboType){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sbHql = new StringBuilder();
		
		sbHql.append("from CmcUCombo where userid=:userid");
		params.put("userid", userId);
		
		if(StringUtil.notBlankAndNull(transType)){
			sbHql.append(" and transtype=:transtype");
			params.put("transtype", transType);
		}
		
		if(comboType!=null){
			sbHql.append(" and combotype=:combotype");
			params.put("combotype", comboType);
		}
		
		sbHql.append(" order by ucid desc");
		
		return (List<CmcUCombo>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户套餐列表
	 * @param ucids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcUCombo> findUserCombo(Set<Long> ucids){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ucids", ucids);
		return (List<CmcUCombo>) qryListByMap("from CmcUCombo where ucid in (:ucids)", params);
	}
	
	/**
	 * 获取用户套餐使用记录
	 * @param ucid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcUCombohis> findHis(Long ucid){
		return (List<CmcUCombohis>) qryList("from CmcUCombohis where ucid=? order by usedtime desc", ucid);
	}
	
	/**
	 * 获取用户翻译套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcUCombo getUserCombo(String orderId){
		return (CmcUCombo) load("from CmcUCombo where orderid=?", orderId);
	}
	
	/**
	 * 获取用户套餐信息
	 * @param exchangeCode
	 * @return
	 */
	public CmcUCombo getUserComboByCode(String exchangeCode){
		return (CmcUCombo) load("from CmcUCombo where exchangecode=?", exchangeCode);
	}
	
	/**
	 * 获取用户翻译套餐信息，带套餐信息
	 * @param ucid
	 * @return
	 */
	public CmcUCombo getUserComboJoinCombo(Long ucid){
		return (CmcUCombo) load("from CmcUCombo t left join fetch t.cmcCombo where ucid=?", ucid);
	}
	
	/**
	 * 获取用户翻译套餐使用的已完成的订单数
	 * @param ucid not null
	 * @param operDate
	 * @return
	 */
	public Long getUserComboUsedCount(Long ucid, Date operDate){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(orderid) from CmcROrder where ucid=:ucid");
		params.put("ucid", ucid);
		
		if(operDate!=null){
			sbHql.append(" and opertime between :stime and :etime");
			params.put("stime", DateUtil.setMinTime(operDate));
			params.put("etime", DateUtil.setMaxTime(operDate));
		}
		
		sbHql.append(" and dealstatus=:dealstatus");
		params.put("dealstatus", DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		
		return (Long) load(sbHql.toString(), params);				
	}
	
	/**
	 * 创建用户翻译套餐序号
	 * @return
	 * 修改历史：
	 */
	public Long newUserComboSeq(){
		BigInteger nextSeq = (BigInteger) getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select nextval('SEQ_CMC_USERCOMBO')").uniqueResult();
			}
		});

		return nextSeq.longValue()*1000+NumberUtil.random(999, 100);
	}
	
	/**
	 * 删除使用记录
	 * @param orderId
	 * @return
	 */
	public void deleteHis(String orderId){
		update("delete from CmcUCombohis where usedorderid=?", orderId);
	}
}
