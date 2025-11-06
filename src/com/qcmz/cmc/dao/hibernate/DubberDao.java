package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IDubberDao;
import com.qcmz.cmc.entity.CmcDubber;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class DubberDao extends BaseDAO implements IDubberDao {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcDubber>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcDubber t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.dubberid) from CmcDubber t");
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
			param = map.get("fullname");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and upper(t.fullname) like :fullname");
				params.put("fullname", "%"+param.toUpperCase()+"%");
			}
			param = map.get("fullcatid");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and t.fullcatid like :fullcatid");
				params.put("fullcatid", param+"%");
			}
			param = map.get("status");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(param));	
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by t.dubberid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取有效的配音员
	 * @param catId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcDubber> findValidDubber(Long catId){
		StringBuilder sbHql = new StringBuilder("from CmcDubber t where t.status=:status");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", SystemConstants.STATUS_ON);
		
		if(catId!=null){
			sbHql.append(" and t.catid=:catid");
			params.put("catid", catId);
		}
		
		sbHql.append(" order by t.sortindex, t.dubberid");
		
		return (List<CmcDubber>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 更新状态
	 * @param dubberId
	 * @param status
	 */
	public void updateStatus(Long dubberId, Integer status){
		String hql = "update CmcDubber set status=:status where dubberid=:dubberid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dubberid", dubberId);
		params.put("status", status);

		updateBatch(hql, params);
	}
}
