package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILocalTaskSubDao;
import com.qcmz.cmc.entity.CmcLtSub;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class LocalTaskSubDao extends BaseDAO implements ILocalTaskSubDao {
	/**
	 * 分页查询用户订阅
	 * @param map
	 * @param pageBean<CmcLtSub>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcLtSub t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.subid) from CmcLtSub t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//根据Map参数添加查询条件
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			String param = null;
			
			param = map.get("title");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and upper(t.title) like :title");
				params.put("title", "%"+param.toUpperCase()+"%");
			}
			param = map.get("cityid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.cityid=:cityid");
				params.put("cityid", Long.valueOf(param));	
			}
			param = map.get("userid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(param));	
			}
			param = map.get("worktimetype");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.worktimetype=:worktimetype");
				params.put("worktimetype", Integer.valueOf(param));	
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by t.subid desc");
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	
	/**
	 * 获取用户订阅
	 * @param userId
	 * @return
	 */
	public CmcLtSub getSubByUserId(Long userId){
		String hql = "from CmcLtSub t where t.userid=:userid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		
		return (CmcLtSub) load(hql, params);
	}
	
	/**
	 * 分页获取待通知的订阅
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtSub> findSub4Notify(int pageSize, Long lastId){
		StringBuilder sbHql = new StringBuilder("from CmcLtSub t where (t.notifytime is null or t.notifytime<:notifytime)");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notifytime", DateUtil.getSysDateOnly());	//1天1次
		
		if(lastId!=null){
			sbHql.append(" and t.subid>:subid");
			params.put("subid", lastId);
		}
		
		sbHql.append(" order by t.subid");
		
		return (List<CmcLtSub>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 更新通知时间为当前时间
	 * @param subIds
	 */
	public void updateNotifyTime(List<Long> subIds){
		if(subIds==null || subIds.isEmpty()) return;
		
		StringBuilder sbHql = new StringBuilder("update CmcLtSub t set t.notifytime=:notifytime where");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notifytime", DateUtil.getSysDate());
		
		if(subIds.size()==1){
			sbHql.append(" subid=:subid");
			params.put("subid", subIds.get(0));
		}else{
			sbHql.append(" subid in (:subids)");
			params.put("subids", subIds);
		}
		
		updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 删除用户订阅
	 * @param userId
	 */
	public void deleteSubByUserId(Long userId){
		String hql = "delete from CmcLtSub t where t.userid=:userid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		
		deleteByMap(hql, params);
	}
}
