package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILocalTaskSpiderDao;
import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class LocalTaskSpiderDao extends BaseDAO implements ILocalTaskSpiderDao {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtSpd>，带来源
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcLtSpd t left join fetch t.cmcLtSource");
		StringBuilder sbCountHql = new StringBuilder("select count(t.spdid) from CmcLtSpd t left join t.cmcLtSource");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//根据Map参数添加查询条件
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			String param = null;
			
			param = map.get("spdtaskid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.spdtaskid=:spdtaskid");
				params.put("spdtaskid", Long.valueOf(param));
			}
			
			param = map.get("operator");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and (t.opercd=:operator or t.opername=:operator)");
				params.put("operator", param);
			}
			param = map.get("dealprogress");
			if(StringUtil.notBlankAndNull(param)){
				String[] arrDealProgress = StringUtil.split(param, ",");
				if(arrDealProgress.length==1){
					sbCond.append(" and t.dealprogress=:dealprogress");
					params.put("dealprogress", arrDealProgress[0]);
				}else if(arrDealProgress.length>1){
					sbCond.append(" and t.dealprogress in (:dealprogress)");
					params.put("dealprogress", arrDealProgress);
				}
			}
			param = map.get("sourcename");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and t.cmcLtSource.sourcename like :sourcename");
				params.put("sourcename", "%"+param+"%");
			}
			param = map.get("cityid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.cmcLtSource.cityid=:cityid");
				params.put("cityid", Long.valueOf(param));
			}
			param = map.get("title");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and upper(t.title) like :title");
				params.put("title", "%"+param.toUpperCase()+"%");
			}
			param = map.get("spddate");
			if(StringUtil.notBlankAndNull(param)){
				Date spiderTimeS = DateUtil.parseDate(param);
				Date spiderTimeE = DateUtil.setMaxTime(spiderTimeS);
				sbCond.append(" and t.spdtime>=:start and t.spdtime<=:end");
				params.put("start", spiderTimeS);
				params.put("end", spiderTimeE);
			}
						
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by publishtime desc, t.spdid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取采集列表
	 * @param dealProgress
	 * @param spiderTimeS
	 * @param spiderTimeE
	 * @param pageSize
	 * @param lastSpdId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtSpd> findSpd(String dealProgress, Date spiderTimeS, Date spiderTimeE, int pageSize, Long lastSpdId){
		StringBuilder sbHql = new StringBuilder("from CmcLtSpd t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		gantCondition(sbCond, params, dealProgress, spiderTimeS, spiderTimeE, lastSpdId);
		
		
		if(sbCond.length()>0){
			sbCond.replace(0, 4, " where");
			sbHql.append(sbCond);
		}
		
		return (List<CmcLtSpd>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取采集数
	 * @param dealProgress
	 * @param spiderTimeS
	 * @param spiderTimeE
	 * @param lastSpdId
	 * @return
	 */
	public Long getSpdCount(String dealProgress, Date spiderTimeS, Date spiderTimeE, Long lastSpdId){
		StringBuilder sbHql = new StringBuilder("select count(*) from CmcLtSpd t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		gantCondition(sbCond, params, dealProgress, spiderTimeS, spiderTimeE, lastSpdId);
		
		if(sbCond.length()>0){
			sbCond.replace(0, 4, " where");
			sbHql.append(sbCond);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	private void gantCondition(StringBuilder sbCond, Map<String, Object> params, String dealProgress, Date spiderTimeS, Date spiderTimeE, Long lastSpdId){
		if(StringUtil.notBlankAndNull(dealProgress)){
			sbCond.append(" and t.dealprogress=:dealprogress");
			params.put("dealprogress", dealProgress);
		}
		if(spiderTimeS!=null){
			sbCond.append(" and t.spdtime>=:start");
			params.put("start", spiderTimeS);
		}
		if(spiderTimeE!=null){
			sbCond.append(" and t.spdtime<=:end");
			params.put("end", spiderTimeE);
		}
		
		if(lastSpdId!=null){
			sbCond.append(" and t.spdid>:spdid");
			params.put("spdid", lastSpdId);
		}
		//sbCond.append(" and t.spdid=26581");
	}
	
	/**
	 * 获取采集信息，带来源
	 * @param spdId
	 * @return
	 */
	public CmcLtSpd getSpdJoin(Long spdId){
		String hql = "from CmcLtSpd t left join fetch t.cmcLtSource where t.spdid=:spdid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spdid", spdId);
		
		return (CmcLtSpd) load(hql, params);
	}
	
	/**
	 * 是否存在
	 * @param sourceId
	 * @param title
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean exist(Long sourceId, String title){
		String hql = "select spdid from CmcLtSpd where sourceid=:sourceid and title=:title";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceid", sourceId);
		params.put("title", title);
		List<Long> list = (List<Long>) qryListTop(hql, params, 1);
		return !list.isEmpty();
	}
}
