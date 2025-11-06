package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITradeDao;
import com.qcmz.cmc.entity.CmcFeeRule;
import com.qcmz.cmc.entity.CmcFeeRuledetail;
import com.qcmz.cmc.entity.CmcFeeTrade;
import com.qcmz.cmc.ws.provide.vo.ConsumeRuleDetailBean;
import com.qcmz.cmc.ws.provide.vo.TradeQueryBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class TradeDao extends BaseDAO implements ITradeDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcFeeTrade t left join fetch t.cmcProxy");
		StringBuilder sbCountHql = new StringBuilder("select count(t.tradeid) from CmcFeeTrade t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
			
			//交易类型
			String tradetype = map.get("tradetype");
			if(StringUtil.notBlankAndNull(tradetype)){
				sbCond.append(" and t.tradetype=:tradetype");
				params.put("tradetype", tradetype);
			}
			
			//交易时间
			String begin = map.get("begin");
			if(StringUtil.notBlankAndNull(begin)){
				sbCond.append(" and t.tradetime>=:begin");
				params.put("begin", DateUtil.parseDate(begin));
			}
			String end = map.get("end");
			if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.tradetime<:end");
				params.put("end", DateUtil.addDay(DateUtil.parseDate(end),1));
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.tradeid desc");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取用户交易列表
	 * @param search
	 * @param pageBean
	 * 修改历史：
	 */
	public void findTrade(TradeQueryBean search, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcFeeTrade t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.tradeid) from CmcFeeTrade t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//查询条件
		StringBuilder sbCond = new StringBuilder();
		sbCond.append(" where t.userid=:userid");
		params.put("userid", search.getUserId());
		
		//交易类型
		if(StringUtil.notBlankAndNull(search.getTradeType())){
			sbCond.append(" and t.tradetype=:tradetype");
			params.put("tradetype", search.getTradeType());
		}
		
		//交易时间
		if(search.getBegin()!=null){
			sbCond.append(" and t.tradetime>=:begin");
			params.put("begin", search.getBegin());
		}
		
		if(search.getEnd()!=null){
			sbCond.append(" and t.tradetime<=:end");
			params.put("end", DateUtil.addDay(search.getEnd(),1));
		}
		
		sbHql.append(sbCond);
		sbCountHql.append(sbCond);
		
		sbHql.append(" order by t.tradeid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取用户某产品充值记录
	 * @param userId
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcFeeTrade> findTrade(Long userId, Long productId){
		return (List<CmcFeeTrade>) qryList("from CmcFeeTrade t where t.userid=? and t.productid=?", new Object[]{userId, productId});
	}
	
	/**
	 * 指定用户是否已经充值过某产品
	 * @param userId
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public boolean existTrade(Long userId, Long productId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		params.put("productid", productId);
		
		Long count = (Long) load("select count(tradeid) from CmcFeeTrade t where t.userid=:userid and t.productid=:productid", params);
		return count>0;
	}
	
	/**
	 * 获取用户已经领取的免费产品
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findFreeProduct(Long userId){
		Object[] params = new Object[]{userId, "01"};
		return (List<Long>) qryList("select distinct t.productid from CmcFeeTrade t where t.userid=? and t.tradetype=? and t.amount=0", params);
	}
	
	/**
	 * 获取用户已经充值的非消耗产品
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findUnconsumableProduct(Long userId){
		Object[] params = new Object[]{userId, "01", DictConstant.DICT_PRODUCTTYPE_UNCONSUMABLE};
		return (List<Long>) qryList("select distinct t.productid from CmcFeeTrade t where t.userid=? and t.tradetype=? and t.cmcFeeProduct.producttype=?", params);
	}
	
	/**
	 * 获取所有有效的消费规则列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcFeeRule> findValidRule(){
		return (List<CmcFeeRule>) qryList("from CmcFeeRule t where t.status=1");
	}
	
	/**
	 * 获取指定消费规则的细则
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcFeeRuledetail> findRuleDetail(Long ruleId){
		return (List<CmcFeeRuledetail>) qryList("from CmcFeeRuledetail t where t.cmcFeeRule.ruleid=?", ruleId);
	}
	
	/**
	 * 获取指定消费规则的细则
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<ConsumeRuleDetailBean> findRuleDetailInfo(Long ruleId){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.ConsumeRuleDetailBean(")
			 .append("item, langcode, point")
			 .append(")from CmcFeeRuledetail t")
			 .append(" where t.cmcFeeRule.ruleid=?");
		return (List<ConsumeRuleDetailBean>) qryList(sbHql.toString(), ruleId);
	}
}
