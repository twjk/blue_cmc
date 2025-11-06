package com.qcmz.cmc.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IUserCrowdTaskDao;
import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.entity.CmcCtUsersubject;
import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcQueryBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyQueryBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class UserCrowdTaskDao extends BaseDAO implements IUserCrowdTaskDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtUsertask，带CmcCtTask>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcCtUsertask t left join fetch t.cmcCtTask");
		StringBuilder sbCountHql = new StringBuilder("select count(t.utid) from CmcCtUsertask t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			//用户任务编号
			String utid = map.get("utid");
			if(StringUtil.notBlankAndNull(utid)){
				if(utid.length()==18){
					sbCond.append(" and t.utid = :utid");
					params.put("utid", utid);
				}else{
					sbCond.append(" and t.utid like :utid");
					params.put("utid", "%"+utid);
				}				
			}
			//任务编号
			String taskid = map.get("taskid");
			if(NumberUtil.isNumber(taskid)){
				sbCond.append(" and t.taskid=:taskid");
				params.put("taskid", Long.valueOf(taskid));
			}
			//任务名称
			String title = map.get("title");
			if(StringUtil.notBlankAndNull(title)){
				sbCond.append(" and t.cmcCtTask.title like :title");
				params.put("title", "%"+title+"%");
			}
			//业务类型
			String serviceType = map.get("servicetype");
			if(StringUtil.notBlankAndNull(serviceType)){
				sbCond.append(" and t.servicetype=:servicetype");
				params.put("servicetype", serviceType);
			}
			//任务来源
			String tasksource = map.get("tasksource");
			if(NumberUtil.isNumber(tasksource)){
				sbCond.append(" and t.cmcCtTask.tasksource=:tasksource");
				params.put("tasksource", Integer.valueOf(tasksource));
			}
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
			//用户uuid
			String uuid = map.get("uuid");
			if(StringUtil.notBlankAndNull(uuid)){
				sbCond.append(" and t.uuid=:uuid");
				params.put("uuid", uuid);
			}
			//用户任务状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}
			//下单平台
			String platform = map.get("platform");
			if(StringUtil.notBlankAndNull(platform)){
				sbCond.append(" and t.platform=:platform");
				params.put("platform", platform);
			}
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.utid desc");
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取最近完成任务的用户编号
	 * @param taskId
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findFinishUserId(Long taskId, int limit){
		String hql = "select userid from CmcCtUsertask t where t.taskid=:taskid and t.status=:status order by finishtime desc";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		params.put("status", DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
		
		return (List<Long>) qryListTop(hql, params, limit);
	}
	
	/**
	 * 分页获取用户任务列表，带任务信息
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsertask> findUserTaskJoin(Long userId, int pageSize, String lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtUsertask t left join fetch t.cmcCtTask where t.userid=:userid");
		params.put("userid", userId);
		
		if(StringUtil.notBlankAndNull(lastId)){
			sbHql.append(" and t.utid<:utid");
			params.put("utid", lastId);
		}
		
		sbHql.append(" order by t.utid desc");
		
		return (List<CmcCtUsertask>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取用户指定任务列表
	 * @param userId
	 * @param taskIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsertask> findUserTask(Long userId, Set<Long> taskIds){
		String hql = "from CmcCtUsertask where userid=:userid and taskid in(:taskids) order by utid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		params.put("taskids", taskIds);
		
		return (List<CmcCtUsertask>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取用户的用户任务
	 * @param userId
	 * @param statuses
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsertask> findUserTask(Long userId, List<Integer> statuses){
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "from CmcCtUsertask where userid=:userid and status in(:statuses)";

		params.put("userid", userId);
		params.put("statuses", statuses);

		return (List<CmcCtUsertask>) qryListByMap(hql, params);
	}
	
	/**
	 * 分页获取用户任务列表
	 * @param taskId
	 * @param status
	 * @param minFinishTime
	 * @param maxFinishTime
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsertask> findUserTaskByTaskId(Long taskId, Integer status, Date minFinishTime, Date maxFinishTime, int pageSize, String lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtUsertask t where t.taskid=:taskid");
		params.put("taskid", taskId);
		
		if(status!=null){
			sbHql.append(" and t.status=:status");
			params.put("status", status);
		}
		
		if(minFinishTime!=null){
			sbHql.append(" and t.finishtime>=:minfinishtime");
			params.put("minfinishtime", minFinishTime);
		}
		
		if(maxFinishTime!=null){
			sbHql.append(" and t.finishtime<=:maxfinishtime");
			params.put("maxfinishtime", maxFinishTime);
		}
		
		if(StringUtil.notBlankAndNull(lastId)){
			sbHql.append(" and t.utid>:utid");
			params.put("utid", lastId);
		}
		
		sbHql.append(" order by t.utid");
		
		return (List<CmcCtUsertask>) qryListTop(sbHql.toString(), params, pageSize);
	}	
	
	/**
	 * 根据设备码获取用户任务编号列表
	 * @param taskId
	 * @param uuid
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findUserTaskByUuid(Long taskId, String uuid, Integer status){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select utid from CmcCtUsertask where taskid=:taskid and uuid=:uuid");
		params.put("taskid", taskId);
		params.put("uuid", uuid);
		
		if(status!=null){
			sbHql.append(" and status=:status");
			params.put("status", status);
		}
		
		sbHql.append(" order by utid");
		
		return (List<String>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 分页获取用户任务报名审核列表
	 * @param query
	 * @param serviceType
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserCrowdTaskVerifyListBean> findUserTask4Verify(UserCrowdTaskVerifyQueryBean query, String serviceType, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sortIdSql = "t.utid";
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyListBean(")
			 .append(sortIdSql)
			 .append(", t.utid, t.cmcCtTask.title, t.cmcCtTask.pic, t.applytime, t.countryname, t.cityname")
			 .append(", t.verifyreward, t.cmcCtTask.rewardtype, t.cmcCtTask.unitname, t.verifyrewardtime")
			 .append(", t.verifystatus, t.verifystarttime, t.verifyfinishtime")
			 .append(") from CmcCtUsertask t")
			 ;
		
		sbHql.append(" where t.servicetype=:servicetype");
		params.put("servicetype", serviceType);
		
		if(query.getVerifyStatus()==null){
			sbHql.append(" and t.verifystatus is not null");
		}else{
			sbHql.append(" and t.verifystatus=:verifystatus");
			params.put("verifystatus", query.getVerifyStatus());
		}
		
		if(query.getVerifyUid()!=null){
			sbHql.append(" and t.verifyuserid=:verifyuserid");
			params.put("verifyuserid", query.getVerifyUid());
		}
		
		if(DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_DEALT.equals(query.getVerifyStatus())){
			//已完成倒序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbHql.append(" and ").append(sortIdSql).append("<:baseid");
				params.put("baseid", query.getMoreBaseId());
			}
			
			sbHql.append(" order by 1 desc");
		}else{
			//正序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbHql.append(" and ").append(sortIdSql).append(">:baseid");
				params.put("baseid", query.getMoreBaseId());
			}
					
			sbHql.append(" order by 1");
		}
		
		return (List<UserCrowdTaskVerifyListBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取提醒用户完成的用户任务列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsertask> findUserTask4RemindToFinish(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtUsertask t left join fetch t.cmcCtTask where t.status in(:statuses)")
			 .append(" and t.verifyfinishtime between :start and :end")
			 .append(" and t.cmcCtTask.taskrewardsettle=:settlemethod")
			 .append(" order by t.utid")
			 ;
		params.put("statuses", DictConstant.DICT_USERCROWDTASK_STATUSES_ING);
		params.put("settlemethod", DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK);
		
		Date now = new Date();
		params.put("start", DateUtil.addDay(now, -3));
		params.put("end", DateUtil.addMinute(now, -120));
		
		return (List<CmcCtUsertask>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 分页获取用户任务审校列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserCrowdTaskQcListBean> findUserTask4Qc(UserCrowdTaskQcQueryBean query, String serviceType, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sortIdSql;
		if(DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT.equals(query.getQcStatus())){
			sortIdSql = "CONCAT(unix_timestamp(t.qcfinishtime), t.utid)";
		}else{
			sortIdSql = "CONCAT(unix_timestamp(t.createtime), t.utid)";
		}
		
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcListBean(")
			 .append(sortIdSql)
			 .append(", t.qcid, t.utid, t.cmcCtTask.title, t.cmcCtTask.pic, t.cmcCtUsertask.finishsubjectnum, t.createtime")
			 .append(", t.qcstatus, t.qcstarttime, t.qcfinishtime, t.qcfinishnum")
			 .append(", t.cmcCtTask.qcreward, t.cmcCtTask.qcrewardmeasure, t.cmcCtTask.rewardtype, t.cmcCtTask.unitname, t.qcrewardtime")
			 .append(") from CmcCtQc t ")
			 ;

		sbHql.append(" where t.cmcCtUsertask.servicetype=:servicetype");
		params.put("servicetype", serviceType);
		
		if(query.getQcStatus()==null){
			sbHql.append(" and t.qcstatus is not null");
		}else{
			sbHql.append(" and t.qcstatus=:qcstatus");
			params.put("qcstatus", query.getQcStatus());
		}
		
		if(query.getQcUid()!=null){
			sbHql.append(" and t.qcuserid=:qcuserid");
			params.put("qcuserid", query.getQcUid());
		}
		
		if(DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT.equals(query.getQcStatus())){
			//已完成倒序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbHql.append(" and ").append(sortIdSql).append("<:baseid");
				params.put("baseid", query.getMoreBaseId());
			}
			
			sbHql.append(" order by 1 desc");
		}else{
			//正序
			if(StringUtil.notBlankAndNull(query.getMoreBaseId())){
				sbHql.append(" and ").append(sortIdSql).append(">:baseid");
				params.put("baseid", query.getMoreBaseId());
			}
					
			sbHql.append(" order by 1");
		}
		
		return (List<UserCrowdTaskQcListBean>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取待发放任务奖励的列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findUserTask4GrantTaskReward(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select utid from CmcCtUsertask t where t.utrewardstatus=:status and t.cmcCtTask.taskrewardsettle=:rewardsettle");
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		params.put("rewardsettle", DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK);
		
		/*
		//延时发放奖励
		sbHql.append(" and qcfinishtime<=:time");
		params.put("time", DateUtil.add(new Date(), DateUtil.HOUR, -20));
		*/
		
		return (List<String>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取待发放审核奖励的列表 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findUserTask4GrantVerifyReward(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select utid from CmcCtUsertask where verifyrewardstatus=:status");
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		
		/*
		//延时发放奖励
		sbHql.append(" and verifyfinishtime<=:time");
		params.put("time", DateUtil.add(new Date(), DateUtil.HOUR, -20));
		*/
		
		return (List<String>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户任务数
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public Long getCount(Long userId, List<Integer> statuses){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(utid) from CmcCtUsertask where userid=:userid and status in(:statuses)");
		params.put("userid", userId);

		if(statuses!=null && !statuses.isEmpty()){
			if(statuses.size()==1){
				sbHql.append(" and status=:status");
				params.put("status", statuses.get(0));
			}else{
				sbHql.append(" and status in(:statuses)");
				params.put("statuses", statuses);
			}
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 根据设备码获取用户任务数
	 * @param taskId
	 * @param uuid
	 * @return
	 */
	public Long getCountByUuid(Long taskId, String uuid){
		String hql = "select count(utid) from CmcCtUsertask where taskid=:taskid and uuid=:uuid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		params.put("uuid", uuid);
		
		return (Long) load(hql, params);
	}
	
	/**
	 * 获取审核数量
	 * @param serviceType not null
	 * @param verifyStatus not null
	 * @param verifyUserId
	 * @return
	 */
	public Long getVerifyCount(String serviceType, Integer verifyStatus, Long verifyUserId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(utid) from CmcCtUsertask where servicetype=:servicetype and verifystatus=:verifystatus");
		params.put("servicetype", serviceType);
		params.put("verifystatus", verifyStatus);
		
		if(verifyUserId!=null){
			sbHql.append(" and verifyuserid=:verifyuserid");
			params.put("verifyuserid", verifyUserId);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取审校数量
	 * @param serviceType not null
	 * @param qcStatus not null
	 * @param qcUserId
	 * @return
	 */
	public Long getQcCount(String serviceType, Integer qcStatus, Long qcUserId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(qcid) from CmcCtQc t where t.cmcCtUsertask.servicetype=:servicetype and t.qcstatus=:qcstatus");
		params.put("servicetype", serviceType);
		params.put("qcstatus", qcStatus);
		
		if(qcUserId!=null){
			sbHql.append(" and t.qcuserid=:qcuserid");
			params.put("qcuserid", qcUserId);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取超时未发放任务奖励的数量
	 * @return
	 */
	public Long getCount4UngrantTaskReward(){
		String hql = "select count(utid) from CmcCtUsertask where utrewardstatus=:status";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		
		return (Long) load(hql, params);
	}
	
	/**
	 * 获取超时未发放审核奖励的数量
	 * @return
	 */
	public Long getCount4UngrantVerifyReward(){
		String hql = "select count(utid) from CmcCtUsertask where verifyrewardstatus=:status and verifyfinishtime<=:time";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		params.put("time", DateUtil.addDay(new Date(), -2));
		
		return (Long) load(hql, params);
	}
	
	/**
	 * 获取用户任务，带任务信息
	 * @param utId
	 * @return
	 */
	public CmcCtUsertask getUserTaskJoin(String utId){
		String hql = "from CmcCtUsertask t left join fetch t.cmcCtTask where utid=:utid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utid", utId);
		
		return (CmcCtUsertask) load(hql, params);
	}
	
	/**
	 * 获取用户任务
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public CmcCtUsertask getUserTask(Long userId, Long taskId){
		String hql = "from CmcCtUsertask where userid=:userid and taskid=:taskid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		params.put("taskid", taskId);
		
		return (CmcCtUsertask) load(hql, params);
	}
	
	/**
	 * 用户是否已参与同组任务（已取消的不算）
	 * @param userId not null
	 * @param groupCode not null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isPartInGroupTask(Long userId, String groupCode){
		String hql = "select ut.utid from CmcCtUsertask ut where ut.userid=:userid and ut.cmcCtTask.groupcode=:groupcode and ut.status!=:status";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		params.put("groupcode", groupCode);
		params.put("status", DictConstant.DICT_USERCROWDTASK_STATUS_CANCEL);
		
		//老逻辑可能出现多条记录，用qryListTop而不用load
		List<Long> list = (List<Long>) qryListTop(hql, params, 1);
		
		return !list.isEmpty();
	}
	
	/**
	 * 获取新的用户任务编号
	 * @return
	 * 修改历史：
	 */
	public String newUtId(){
		BigInteger nextSeq = (BigInteger) getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select nextval('SEQ_CMC_USERCROWDTASK')").uniqueResult();
			}
		});

		String seq = StringUtil.right(String.valueOf(nextSeq.longValue()), 6);
		
		return new StringBuilder()
			.append(DateUtil.format(new Date(), "yyMMddHHmmss"))
			.append(seq)
			.toString()
			;
	}
	
	/**
	 * 更新国家和城市
	 * @param utId
	 * @param countryName
	 * @param cityName
	 */
	public void updateCountryAndCity(String utId, String countryName, String cityName){
		String hql = "update CmcCtUsertask set countryname=:countryname, cityname=:cityname where utid=:utid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utid", utId);
		params.put("countryname", countryName);
		params.put("cityname", cityName);
		
		updateBatch(hql, params);
	}
	
	/**
	 * 更新用户任务题目数
	 * @param utId
	 */
	public void updateSubjectNum(String utId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcCtUsertask set subjectnum=(")
			 .append("select count(subjectid) from CmcCtUsersubject where utid=:utid and tasktype=:tasktype")
			 .append(") where utid=:utid")
			 ;
		
		params.put("utid", utId);
		params.put("tasktype", DictConstant.DICT_CROWDTASK_TASKTYPE_TASK);
		
		updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 更新用户任务已完成题目数
	 * @param utId
	 */
	public void updateFinishSubjectNum(String utId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcCtUsertask set finishsubjectnum=(")
			 .append("select count(distinct subjectid) from CmcCtUseranswer where utid=:utid and tasktype=:tasktype")
			 .append(") where utid=:utid")
			 ;
		
		params.put("utid", utId);
		params.put("tasktype", DictConstant.DICT_CROWDTASK_TASKTYPE_TASK);
		
		updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 更新用户奖励到账状态
	 * @param utId
	 * @param utRewardStatus
	 */
	public void updateUtRewardStatus(String utId, Integer utRewardStatus){
		String hql = "update CmcCtUsertask set utrewardstatus=:utrewardstatus where utid=:utid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utrewardstatus", utRewardStatus);
		params.put("utid", utId);
		
		updateBatch(hql, params);
	}
	
	/**
	 * 更新用户奖励
	 * @param utId
	 */
	public void updateUtReward(String utId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("update CmcCtUsertask set utrewardstatus=:utrewardstatus, utrewardtime=:utrewardtime")
			 .append(", utreward=(select coalesce(sum(usreward),0) from CmcCtUsersubject where utid=:utid)")
			 .append(" where utid=:utid");
		params.put("utrewardstatus", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_DEALT);
		params.put("utrewardtime", new Date());
		params.put("utid", utId);
		
		updateBatch(sbHql.toString(), params);
	}
		
	/**
	 * 获取用户任务题目列表
	 * @param utId not null
	 * @param taskId not null
	 * @param subjectIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsersubject> findUserSubject(String utId, Long taskId, List<Long> subjectIds){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("from CmcCtUsersubject where utid=:utid and taskid=:taskid");
		params.put("utid", utId);
		params.put("taskid", taskId);

		if(subjectIds!=null && !subjectIds.isEmpty()){
			if(subjectIds.size()==1){
				sbHql.append(" and subjectid=:subjectid");
				params.put("subjectid", subjectIds.get(0));
			}else{
				sbHql.append(" and subjectid in(:subjectids)");
				params.put("subjectids", subjectIds);
			}
		}
				
		sbHql.append(" order by subjectid");
		
		return (List<CmcCtUsersubject>) qryListByMap(sbHql.toString(), params);
	}
		
	/**
	 * 获取用户任务题目列表，带CmcCtSubject
	 * @param utId
	 * @param taskId
	 * @param qcId
	 * @param qcStatuses
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUsersubject> findUserSubjectJoin(String utId, Long taskId, Long qcId, List<Integer> qcStatuses){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("from CmcCtUsersubject t left join fetch t.cmcCtSubject where t.utid=:utid and t.taskid=:taskid");
		params.put("utid", utId);
		params.put("taskid", taskId);

		if(qcId!=null){
			sbHql.append(" and t.qcid=:qcid");
			params.put("qcid", qcId);
		}
		if(qcStatuses!=null && !qcStatuses.isEmpty()){
			sbHql.append(" and t.qcstatus in(:qcstatus)");
			params.put("qcstatus", qcStatuses);
		}

		sbHql.append(" order by t.subjectid");
		
		return (List<CmcCtUsersubject>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户任务题目编号列表
	 * @param utId not null
	 * @param taskId not null
	 * @param subjectIds
	 * @param answerStatus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findUserSubjectId(String utId, Long taskId, List<Long> subjectIds, Integer answerStatus){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select subjectid from CmcCtUsersubject where utid=:utid and taskid=:taskid");
		params.put("utid", utId);
		params.put("taskid", taskId);
		
		if(subjectIds!=null && !subjectIds.isEmpty()){
			if(subjectIds.size()==1){
				sbHql.append(" and subjectid=:subjectid");
				params.put("subjectid", subjectIds.get(0));
			}else{
				sbHql.append(" and subjectid in(:subjectids)");
				params.put("subjectids", subjectIds);
			}
		}
		
		if(answerStatus!=null){
			sbHql.append(" and answerstatus=:answerstatus");
			params.put("answerstatus", answerStatus);
		}
		
		sbHql.append(" order by subjectid");
		
		return (List<Long>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取待发放任务奖励的用户题目列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findUserSubject4GrantTaskReward(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select t.usid from CmcCtUsersubject t where usrewardstatus=:status and t.cmcCtTask.taskrewardsettle=:rewardsettle");
		params.put("status", DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		params.put("rewardsettle", DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT);
		
		return (List<Long>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户题目
	 * @param utId
	 * @param subjectId
	 * @return
	 */
	public CmcCtUsersubject getUserSubject(String utId, Long subjectId){
		String hql = "from CmcCtUsersubject where utid=:utid and subjectid=:subjectid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utid", utId);
		params.put("subjectid", subjectId);
		
		return (CmcCtUsersubject) load(hql, params);
	}
	
	/**
	 * 获取用户题目，带题目、用户任务、任务
	 * @param usId
	 * @return
	 */
	public CmcCtUsersubject getUserSubjectJoin(Long usId){
		String hql = "from CmcCtUsersubject t left join fetch t.cmcCtSubject left join fetch t.cmcCtUsertask left join fetch t.cmcCtTask where t.usid=:usid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("usid", usId);
		
		return (CmcCtUsersubject) load(hql, params);
	}
		
	/**
	 * 获取用户任务最大题目编号
	 * @param utId
	 * @return
	 */
	public Long getMaxSubjectId(String utId){
		String hql = "select max(subjectid) from CmcCtUsersubject where utid=:utid and tasktype=:tasktype";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utid", utId);
		params.put("tasktype", DictConstant.DICT_CROWDTASK_TASKTYPE_TASK);
		
		return (Long) load(hql, params);
	}
	
	/**
	 * 获取用户题目数
	 * @param utId
	 * @param qcStatus
	 * @return
	 */
	public Long getUserSubjectCount(String utId, Integer qcStatus){
		StringBuilder sbHql = new  StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(usid) from CmcCtUsersubject where utid=:utid and tasktype=:tasktype");
		params.put("utid", utId);
		params.put("tasktype", DictConstant.DICT_CROWDTASK_TASKTYPE_TASK);
		
		if(qcStatus!=null){
			sbHql.append(" and qcstatus=:qcstatus");
			params.put("qcstatus", qcStatus);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 更新用户任务题目待审校
	 * @param utId
	 * @param taskId
	 */
	public void updateSubjectWaitQc(String utId, Long taskId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("update CmcCtUsersubject set qcstatus=:qcstatus where utid=:utid and taskid=:taskid and qcstatus is null");
		params.put("utid", utId);
		params.put("taskid", taskId);
		params.put("qcstatus", DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING);
		
		updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 更新用户任务题目得分
	 * @param utId
	 * @param subjectId
	 * @param score
	 */
	public void updateSubjectScore(String utId, Long subjectId, Integer score){
		String hql = "update CmcCtUsersubject set score=:score where utid=:utid and subjectid=:subjectid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("score", score);
		params.put("utid", utId);
		params.put("subjectid", subjectId);
		
		updateBatch(hql, params);
	}
	
	/**
	 * 更新用户任务题目奖励到账状态
	 * @param utId
	 * @param qcId
	 * @param rewardStatus
	 */
	public void updateSubjectRewardStatus(String utId, Long qcId, Integer rewardStatus){
		String hql = "update CmcCtUsersubject set usrewardstatus=:usrewardstatus where utid=:utid and qcid=:qcid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("usrewardstatus", rewardStatus);
		params.put("utid", utId);
		params.put("qcid", qcId);
		
		updateBatch(hql, params);
	}
	
	/**
	 * 获取用户任务答案列表
	 * @param utId
	 * @param taskId
	 * @param subjectIds
	 * @param qcId
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUseranswer> findUserAnswer(String utId, Long taskId, List<Long> subjectIds, Long qcId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(qcId!=null){
			sbHql.append("select t from CmcCtUseranswer t, CmcCtUsersubject s where t.utid=s.utid and t.subjectid=s.subjectid and t.utid=:utid and t.taskid=:taskid and qcid=:qcid");
			params.put("qcid", qcId);
		}else{
			sbHql.append("from CmcCtUseranswer t where t.utid=:utid and t.taskid=:taskid");
		}
		params.put("utid", utId);
		params.put("taskid", taskId);
		
		if(subjectIds!=null && !subjectIds.isEmpty()){
			if(subjectIds.size()==1){
				sbHql.append(" and t.subjectid=:subjectid");
				params.put("subjectid", subjectIds.get(0));
			}else{
				sbHql.append(" and t.subjectid in(:subjectids)");
				params.put("subjectids", subjectIds);
			}
		}
		
		sbHql.append(" order by t.uaid");
		
		return (List<CmcCtUseranswer>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取用户任务答案列表
	 * @param utId
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUseranswer> findUserAnswerJoinSubject(String utId, Long taskId){
		String hql = "from CmcCtUseranswer t left join fetch t.cmcCtSubject where t.utid=:utid and t.taskid=:taskid order by t.uaid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utid", utId);
		params.put("taskid", taskId);
		
		return (List<CmcCtUseranswer>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取任务的用户任务答案列表，带CmcCtUsertask
	 * @param taskId not null
	 * @param taskStatus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUseranswer> findUserAnswerJoin(Long taskId, Integer taskStatus){
		String hql = "from CmcCtUseranswer t left join fetch t.cmcCtUsertask where t.taskid=:taskid and t.cmcCtUsertask.status=:status order by t.uaid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		if(taskStatus!=null){
			params.put("status", taskStatus);
		}
		
		return (List<CmcCtUseranswer>) qryListByMap(hql, params);
	}
	
	/**
	 * 分页获取删除文件的用户任务答案列表
	 * 自动取消、公司任务、已完成/已取消、指定完成/取消日期
	 * @param date 完成/取消日期 not null
	 * @param pageSize not null
	 * @param lastAnswerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtUseranswer> findUserAnswer4DeleteFile(Date date, int pageSize, Long lastAnswerId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtUseranswer ua where ua.cmcCtTask.autocancel=:autocancel and ua.cmcCtTask.tasksource=:tasksource")
			 .append(" and ua.cmcCtUsertask.status in (:utstatuses)")
			 .append(" and (ua.cmcCtUsertask.finishtime between :timeS and :timeE or ua.cmcCtUsertask.canceltime between :timeS and :timeE)")
			 .append(" and ((ua.pic is not null and ua.pic!='') or (ua.voice is not null and ua.voice!=''))")
			 ;
		
		params.put("autocancel", SystemConstants.STATUS_ON);
		params.put("tasksource", DictConstant.DICT_CROWDTASK_TASKSOURCE_ENT);
		
		List<Integer> utstatuses = new ArrayList<Integer>();
		utstatuses.add(DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
		utstatuses.add(DictConstant.DICT_USERCROWDTASK_STATUS_CANCEL);
		params.put("utstatuses", utstatuses);
		params.put("timeS", DateUtil.setMinTime(date));
		params.put("timeE", DateUtil.setMaxTime(date));
		
		if(lastAnswerId!=null){
			sbHql.append(" and ua.uaid>:lastAnswerId");
			params.put("lastAnswerId", lastAnswerId);
		}
		
		sbHql.append(" order by ua.uaid");
		
		return (List<CmcCtUseranswer>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	
	/**
	 * 获取用户答案信息，带CmcCtUsertask
	 * @param uaid
	 * @return
	 */
	public CmcCtUseranswer getUserAnswerJoin(Long uaid){
		String hql = "from CmcCtUseranswer t left join fetch t.cmcCtUsertask where t.uaid=:uaid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uaid", uaid);
		
		return (CmcCtUseranswer) load(hql, params);
	}
	
	/**
	 * 清除用户答案
	 * @param utid
	 * @param subjectIds
	 */
	public void clearAnswer(String utId, Set<Long> subjectIds){
		String hql = "delete from CmcCtUseranswer where utid=:utid and subjectid in(:ids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("utid", utId);
		params.put("ids", subjectIds);
		
		deleteByMap(hql, params);
	}
	
	/**
	 * 更新用户任务答案得分
	 * @param uaId
	 * @param score
	 */
	public void updateAnswerScore(Long uaId, Integer score){
		String hql = "update CmcCtUseranswer set score=:score where uaid=:uaid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("score", score);
		params.put("uaid", uaId);
		
		updateBatch(hql, params);
	}

	/**
	 * 批量取消指定任务未完成的用户任务
	 * @param taskId not null
	 * @param reason 取消原因
	 * @param daysAgo 多少天之前申请
	 * @param operCd 操作人账号
	 * @param operName 操作人姓名
	 * @return 取消的用户任务数
	 */
	public int cancelByTaskId(Long taskId, String reason, Integer daysAgo, String operCd, String operName){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		
		sbHql.append("update CmcCtUsertask set")
			 .append(" status=:status ,cancelreason=:cancelreason ,canceltime=:canceltime, cancelcd=:cancelcd, cancelname=:cancelname")
			 .append(" where taskid=:taskid and status in(:statuses)")
		;
		params.put("status", DictConstant.DICT_USERCROWDTASK_STATUS_CANCEL);
		params.put("cancelreason", reason);
		params.put("canceltime", now);
		params.put("cancelcd", operCd);
		params.put("cancelname", operName);
		params.put("taskid", taskId);
		params.put("statuses", DictConstant.DICT_USERCROWDTASK_STATUSES_ING);
		
		if(daysAgo!=null && daysAgo>0){
			sbHql.append(" and applytime<=:applytime");
			params.put("applytime", DateUtil.addDay(now, -daysAgo));
		}
		
		return updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 批量取消指定用户未完成的用户任务
	 * @param userId
	 * @param reason
	 * @param operCd
	 * @param operName
	 * @return
	 */
	public int cancelByUserId(Long userId, String reason, String operCd, String operName){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		
		sbHql.append("update CmcCtUsertask set")
			 .append(" status=:status ,cancelreason=:cancelreason ,canceltime=:canceltime, cancelcd=:cancelcd, cancelname=:cancelname")
			 .append(" where userid=:userid and status in(:statuses)")
		;
		params.put("status", DictConstant.DICT_USERCROWDTASK_STATUS_CANCEL);
		params.put("cancelreason", reason);
		params.put("canceltime", now);
		params.put("cancelcd", operCd);
		params.put("cancelname", operName);
		params.put("userid", userId);
		params.put("statuses", DictConstant.DICT_USERCROWDTASK_STATUSES_ING);

		return updateBatch(sbHql.toString(), params);
	}
}
