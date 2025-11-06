package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ILocalTaskDao;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.vo.LocalTaskPageSearchBean;
import com.qcmz.cmc.vo.LocalTastSearchBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class LocalTaskDao extends BaseDAO implements ILocalTaskDao {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtTask带CmcLtCompany>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcLtTask t left join fetch t.cmcLtCompany left join fetch t.cmcLtSource");
		StringBuilder sbCountHql = new StringBuilder("select count(t.taskid) from CmcLtTask t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//根据Map参数添加查询条件
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			String param = null;
			
			param = map.get("taskid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.taskid=:taskid");
				params.put("taskid", Long.valueOf(param));
			}
			
			param = map.get("companyname");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and t.cmcLtCompany.companyname like :companyname");
				params.put("companyname", "%"+param+"%");
			}
			
			param = map.get("title");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and t.title like :title");
				params.put("title", "%"+param+"%");
			}
			param = map.get("status");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(param));	
			}
			param = map.get("tasksource");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.tasksource=:tasksource");
				params.put("tasksource", Integer.valueOf(param));	
			}
			param = map.get("worktimetype");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.worktimetype=:worktimetype");
				params.put("worktimetype", Integer.valueOf(param));	
			}
			param = map.get("isact");
			if(Boolean.valueOf(param)){
				sbCond.append(" and t.actid is not null");
			}
			param = map.get("cityid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.cityid=:cityid");
				params.put("cityid", Long.valueOf(param));	
			}
			param = map.get("createway");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.createway=:createway");
				params.put("createway", Integer.valueOf(param));	
			}
			param = map.get("sourcename");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and  t.cmcLtSource.sourcename like :sourcename");
				params.put("sourcename", "%"+param+"%");
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by t.taskid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取有效的同城任务列表
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtTask> findTask(LocalTaskPageSearchBean search){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcLtTask t where t.status=1");
		
		grantCondition(search, sbHql, params);
		
		//分页
		if(NumberUtil.isNumber(search.getMoreBaseId())){
			sbHql.append(" and t.sortquery>:baseid");
			params.put("baseid", search.getMoreBaseId());
		}
		
		//排序
		sbHql.append(" order by sortquery");
		
		//查询
		return (List<CmcLtTask>) qryListTop(sbHql.toString(), params, search.getPageSize());
	}
	
	/**
	 * 分页获取同城任务列表
	 * @param pageSize
	 * @param lastTaskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtTask> findTask(int pageSize, Long lastTaskId){
		StringBuilder sbHql = new StringBuilder("from CmcLtTask t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//分页
		if(lastTaskId!=null){
			sbHql.append(" where t.taskid>:baseid");
			params.put("baseid", lastTaskId);
		}
		
		//排序
		sbHql.append(" order by t.taskid");
		
		//查询
		return (List<CmcLtTask>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取用户的同城任务列表
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtTask> findTask(LocalZhaopinJobQueryBean search){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcLtTask t where t.userid=:userid");
		params.put("userid", search.getUid());
		
		if(search.getStatus()!=null){
			sbHql.append(" and t.status=:status");
			params.put("status", search.getStatus());
		}
		if(StringUtil.notBlankAndNull(search.getTitle())){
			sbHql.append(" and t.title like :title");
			params.put("title", "%"+search.getTitle()+"%");
		}
		
		//排序
		sbHql.append(" order by taskid desc");
		
		//查询
		return (List<CmcLtTask>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 查重
	 * @param taskId
	 * @param companyId
	 * @param title
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isRepeat(Long taskId, Long companyId, String title){
		StringBuilder sbHql = new StringBuilder("select taskid from CmcLtTask where title=:title");
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("title", title);
		 
		 if(taskId!=null){
			 sbHql.append(" and taskid!=:taskid");
			 params.put("taskid", taskId);
		 }
		 
		 if(companyId!=null){
			 sbHql.append(" and companyid=:companyid");
			 params.put("companyid", companyId);
		 }else{
			 sbHql.append(" and companyid is null");
		 }
		 
		 List<Long> list = (List<Long>) qryListTop(sbHql.toString(), params, 1);
		 return !list.isEmpty();
	}
	
	/**
	 * 获取有效的同城任务数
	 * @param search
	 * @return
	 */
	public Long getTaskCount(LocalTastSearchBean search){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(*) from CmcLtTask t where t.status=1");
		
		grantCondition(search, sbHql, params);
		
		return (Long) load(sbHql.toString(), params);
	}
	
	private void grantCondition(LocalTastSearchBean search, StringBuilder sbHql, Map<String, Object> params){
		if(search.getCityId()!=null){
			sbHql.append(" and t.cityid in (:cityids)");
			params.put("cityids", new Long[]{0L, search.getCityId()});
			if(StringUtil.notBlankAndNull(search.getAddress())){
				sbHql.append(" and t.address like :address");
				params.put("address", "%"+search.getAddress()+"%");
			}
		}
		if(search.getWrokTimeType()!=null){
			sbHql.append(" and t.worktimetype=:worktimetype");
			params.put("worktimetype", search.getWrokTimeType());
		}
		if(StringUtil.notBlankAndNull(search.getTitle())){
			sbHql.append(" and t.title like :title");
			params.put("title", "%"+search.getTitle()+"%");
		}
		if(search.getWorkYear()!=null){
			sbHql.append(" and (t.minexp<=:minexp or t.minexp is null)");
			params.put("minexp", search.getWorkYear());
		}
		if(search.getEdu()!=null){
			sbHql.append(" and (t.minedu<=:minedu or t.minedu is null)");
			params.put("minedu", search.getEdu());
		}
		if(search.getRewardType()!=null && search.getReward()!=null){
			if(DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH.equals(search.getRewardType())){
				sbHql.append(" and t.rewardtype in(:rewardtypes) and t.refreward>=:reward");
				params.put("rewardtypes", DictConstant.DICT_LOCALTASK_REWARDTYPES_MY);
				params.put("reward", search.getReward()*12);
			}else if(DictConstant.DICT_LOCALTASK_REWARDTYPE_YEAR.equals(search.getRewardType())){
				sbHql.append(" and t.rewardtype in(:rewardtypes) and t.refreward>=:reward");
				params.put("rewardtypes", DictConstant.DICT_LOCALTASK_REWARDTYPES_MY);
				params.put("reward", search.getReward());
			}else{
				sbHql.append(" and t.rewardtype=:rewardtype and t.refreward>=:reward");
				params.put("rewardtype", search.getRewardType());
				params.put("reward", search.getReward());
			}
		}
		if(search.getCreateTimeGreaterThan()!=null){
			sbHql.append(" and t.createtime>:createtime");
			params.put("createtime", search.getCreateTimeGreaterThan());
		}
	}
	
	/**
	 * 更新状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status){
		String hql = "update CmcLtTask set status=:status where taskid=:taskid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		params.put("status", status);

		updateBatch(hql, params);
	}
}
