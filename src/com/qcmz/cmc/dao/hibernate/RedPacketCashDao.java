package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRedPacketCashDao;
import com.qcmz.cmc.entity.CmcRpCash;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;

/**
 * 红包提现
 */
@Repository
public class RedPacketCashDao extends BaseDAO implements IRedPacketCashDao {
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRpCash>
	 * 修改历史：
	 */
	public void findCash(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRpCash t");
		StringBuilder sbCountHql = new StringBuilder("select count(cashid) from CmcRpCash t");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String orderby = " order by cashid desc";
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			
			//查询条件
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				Integer st = Integer.valueOf(status);
				sbCond.append(" and t.status=:status");
				params.put("status", st);
				if(DictConstant.DICT_REDPACKETCASH_STATUS_APPLY.equals(st)){
					orderby = " order by cashid";
				}
			}
			
			if(sbCond.length()>3){
				sbCond.replace(1, 4, "where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(orderby);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRpCash> findCash(Long userId, Integer status){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcRpCash where userid=:userid");
		params.put("userid", userId);
		
		if(status!=null){
			sbHql.append(" and status=:status");
			params.put("status", status);
		}
	
		sbHql.append(" order by cashid desc");
		
		return (List<CmcRpCash>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取提现数
	 * @param status not null
	 * @return
	 */
	public Long getCashCount(Integer status){
		String hql = "select count(cashid) from CmcRpCash where status=?";
		return (Long) load(hql, status);
	}
}
