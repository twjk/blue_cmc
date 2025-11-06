package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITranslatorCommissionDao;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.vo.TranslatorCommissionBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class TranslatorCommissionDao extends BaseDAO implements ITranslatorCommissionDao {
	/**
	 * 获取译员佣金统计列表
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 * @param operCd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TranslatorCommissionBean> findCommission(Date startDate, Date endDate, String operCd){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.vo.TranslatorCommissionBean(")
			 .append("sum(commissionamount), sum(amount), count(orderid), opercd, opername")
			 .append(") from CmcROrder")
			 .append(" where ordercat=:ordercat")
			 .append(" and dealprogress=:dealprogress")
			 .append(" and dealstatus in(:dealstatus)")
			 .append(" and (opertime between :start and :end)")
			 ;
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);
		params.put("dealprogress", DictConstants.DICT_DEALPROGRESS_DEALT);
		params.put("dealstatus", DictConstant.DICT_ORDER_DEALSTATUS_DEALT);
		params.put("start", DateUtil.setMinTime(startDate));
		params.put("end", DateUtil.setMaxTime(endDate));
		
		if(StringUtil.notBlankAndNull(operCd)){
			sbHql.append(" and opercd=:opercd");
			params.put("opercd", operCd);
		}
		
		sbHql.append(" group by opercd, opername")
			 .append(" order by 1 desc, 2 desc")
			 ;
		
		return (List<TranslatorCommissionBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取已处理的订单列表，带使用的用户套餐信息
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 * @param commissionStatus
	 * @param operCd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcROrder> findDealtOrder(Date startDate, Date endDate, Integer commissionStatus, String operCd){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcROrder t left join fetch t.cmcUCombo")
			 .append(" where t.ordercat=:ordercat")
			 .append(" and t.dealprogress=:dealprogress")
			 .append(" and dealstatus in(:dealstatus)")
			 .append(" and (t.opertime between :start and :end)")
			 ;
		params.put("ordercat", DictConstant.DICT_ORDERCAT_TRANS);
		params.put("dealprogress", DictConstants.DICT_DEALPROGRESS_DEALT);
		params.put("dealstatus", DictConstant.DICT_ORDER_DEALSTATUS_DEALT);
		params.put("start", DateUtil.setMinTime(startDate));
		params.put("end", DateUtil.setMaxTime(endDate));
		
		if(commissionStatus!=null){
			sbHql.append(" and t.commissionstatus=:cs");
			params.put("cs", commissionStatus);
		}
		
		if(StringUtil.notBlankAndNull(operCd)){
			sbHql.append(" and t.opercd=:opercd");
			params.put("opercd", operCd);
		}
		
		sbHql.append(" order by t.opertime");
		
		return (List<CmcROrder>) qryListByMap(sbHql.toString(), params);
	}
}
