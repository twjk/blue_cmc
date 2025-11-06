package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ICrowdTaskDao;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class CrowdTaskDao extends BaseDAO implements ICrowdTaskDao{
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtTask 带cmcCtLib>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcCtTask t left join fetch t.cmcCtLib");
		StringBuilder sbCountHql = new StringBuilder("select count(t.taskid) from CmcCtTask t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			//任务编号
			String taskid = map.get("taskid");
			if(NumberUtil.isNumber(taskid)){
				sbCond.append(" and t.taskid=:taskid");
				params.put("taskid", Long.valueOf(taskid));
			}
			
			//名称
			String title = map.get("title");
			if(StringUtil.notBlankAndNull(title)){
				sbCond.append(" and t.title like :title");
				params.put("title", "%"+title+"%");
			}
			
			//类型
			String tasktype = map.get("tasktype");
			if(NumberUtil.isNumber(tasktype)){
				sbCond.append(" and t.tasktype=:tasktype");
				params.put("tasktype", Integer.valueOf(tasktype));
			}
			
			//任务来源
			String tasksource = map.get("tasksource");
			if(NumberUtil.isNumber(tasksource)){
				sbCond.append(" and t.tasksource=:tasksource");
				params.put("tasksource", Integer.valueOf(tasksource));
			}
			
			//任务频率
			String taskfreq = map.get("taskfreq");
			if(NumberUtil.isNumber(taskfreq)){
				sbCond.append(" and t.taskfreq=:taskfreq");
				params.put("taskfreq", Integer.valueOf(taskfreq));
			}
			
			//分组代码
			String groupCode = map.get("groupcode");
			if(StringUtil.notBlankAndNull(groupCode)){
				sbCond.append(" and t.groupcode=:groupcode");
				params.put("groupcode", groupCode);
			}
			
			//订单编号
			String orderId = map.get("orderid");
			if(StringUtil.notBlankAndNull(orderId)){
				if(orderId.length()==18){
					sbCond.append(" and t.orderid = :orderid");
					params.put("orderid", orderId);
				}else{
					sbCond.append(" and t.orderid like :orderid");
					params.put("orderid", "%"+orderId);
				}
			}
			
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.taskid desc");
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取所有任务列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtTask> findTask(){
		return (List<CmcCtTask>) qryList("from CmcCtTask order by sortindex, taskid desc");
	}
	
	/**
	 * 获取已启用任务列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtTask> findOnTask(){
		return (List<CmcCtTask>) qryList("from CmcCtTask where status=1 order by taskid");
	}
	
	
	/**
	 * 获取父任务列表
	 * @param taskFreq
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtTask> findParentTask(Integer taskFreq, Integer status){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtTask where parenttaskid=0");
		
		if(taskFreq!=null){
			sbHql.append(" and taskfreq=:taskfreq");
			params.put("taskfreq", taskFreq);
		}
		if(status!=null){
			sbHql.append(" and status=:status");
			params.put("status", status);
		}
		
		sbHql.append(" order by taskid");
		
		return (List<CmcCtTask>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取需要取消用户任务的任务
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtTask> findTask4Cancel(){
		String hql = "from CmcCtTask t where t.autocancel=1 and t.ingnum>0 and t.endtime<? order by taskid";
		return (List<CmcCtTask>) qryList(hql, new Date());
	}
	
	/**
	 * 获取任务
	 * @param parentTaskId not null
	 * @param date 仅日期 not null
	 * @return
	 */
	public CmcCtTask getTaskByParentId(Long parentTaskId, Date date){
		String hql = "from CmcCtTask t where t.parenttaskid=:parenttaskid and t.starttime>=:date";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parenttaskid", parentTaskId);
		params.put("date", date);
		
		return (CmcCtTask) load(hql, params);
	}
	
	/**
	 * 获取有效任务的最小排序值
	 * @return
	 */
	public Integer getMinValidSortIndex(){
		String hql = "select min(sortindex) from CmcCtTask t where t.endtime>:now and t.status=1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("now", new Date());
		
		return (Integer)load(hql, params);
	}
	
	/**
	 * 更新任务状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status){
		String hql = "update CmcCtTask set status=:status where taskid=:taskid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		params.put("status", status);

		updateBatch(hql, params);
	}
	
	/**
	 * 更新任务描述
	 * @param taskId
	 * @param description
	 */
	public void updateDescription(Long taskId, String description){
		String hql = "update CmcCtTask set description=:description where taskid=:taskid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		params.put("description", description);

		updateBatch(hql, params);
	}
	
	/**
	 * 更新任务报名数
	 * @param taskId
	 */
	public void updateApplyCount(Long taskId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcCtTask t set applynum=(")
			 .append("select count(utid) from CmcCtUsertask ut where ut.taskid=:taskid and ut.status in(:statuses)")
			 .append(") where t.taskid=:taskid")
			 ;
		params.put("taskid", taskId);
		params.put("statuses", DictConstant.DICT_USERCROWDTASK_STATUSES_APPLY);
		
		updateBatch(sbHql.toString(), params);		
	}
	
	/**
	 * 更新进行中任务数
	 * @param taskId
	 */
	public void updateIngCount(Long taskId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcCtTask t set ingnum=(")
			 .append("select count(utid) from CmcCtUsertask ut where ut.taskid=:taskid and ut.status in(:statuses)")
			 .append(") where t.taskid=:taskid")
			 ;
		params.put("taskid", taskId);
		params.put("statuses", DictConstant.DICT_USERCROWDTASK_STATUSES_ING);
		
		updateBatch(sbHql.toString(), params);		
	}
	
	/**
	 * 更新任务完成数
	 * @param taskId
	 */
	public void updateFinishCount(Long taskId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcCtTask t set finishnum=(select count(utid) from CmcCtUsertask ut where ut.taskid=:taskid and status in(:sts))")
			 .append(" where t.taskid=:taskid")
		;
		params.put("taskid", taskId);
		params.put("sts", DictConstant.DICT_USERCROWDTASK_STATUSES_FINISH);
		
		updateBatch(sbHql.toString(), params);		
	}
	
	/**
	 * 取消停止过期任务（父任务除外）
	 */
	public void cancelTask(){
		String hql = "update CmcCtTask t set t.status=0 where t.status=1 and t.autocancel=1 and t.parenttaskid>0 and t.endtime<:now";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("now", new Date());
		
		this.updateBatch(hql, params);
	}
}
