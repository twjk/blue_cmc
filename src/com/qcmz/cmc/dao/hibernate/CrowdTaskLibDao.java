package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ICrowdTaskLibDao;
import com.qcmz.cmc.entity.CmcCtLib;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.vo.CrowdTaskLibLackBean;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class CrowdTaskLibDao extends BaseDAO implements ICrowdTaskLibDao {
	/**
	 * 获取前置任务题库编号列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findLibIdOfPreTask(){
		String hql = "select distinct libid from CmcCtTask where tasktype=:tasktype";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tasktype", DictConstant.DICT_CROWDTASK_TASKTYPE_PRETASK);
		
		return (List<Long>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取题目不足的题库
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CrowdTaskLibLackBean> findLib4Lack(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.vo.CrowdTaskLibLackBean(")
			 .append("s.cmcCtLib.libid, s.cmcCtLib.libname, count(s.subjectid)")
			 .append(") from CmcCtSubject s where s.subjectid>s.cmcCtLib.assignedsubjectid")
			 .append(" group by s.cmcCtLib.libid, s.cmcCtLib.libname, s.cmcCtLib.minidlesubject")
			 .append(" having count(s.subjectid)<s.cmcCtLib.minidlesubject")
		;
		
		return (List<CrowdTaskLibLackBean>) qryList(sbHql.toString());
	}
	
	/**
	 * 根据题库名获取题库
	 * @param libName
	 * @return
	 */
	public CmcCtLib getLibByName(String libName){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtLib t where t.libname=:libname");
		params.put("libname", libName);
		
		return (CmcCtLib) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取题库题目数
	 * @param libId
	 * @return
	 */
	public Long getSubjectCount(Long libId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(subjectid) from CmcCtSubject where libid=:libid");
		params.put("libid", libId);
		
		return (Long) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取题库题目列表
	 * @param libIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtSubject> findSubject(List<Long> libIds){
		String hql = "from CmcCtSubject where libid in(:libids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("libids", libIds);
		
		return (List<CmcCtSubject>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取题目列表
	 * @param subjectIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtSubject> findSubjectBySubjectId(List<Long> subjectIds){
		List<CmcCtSubject> result = new ArrayList<CmcCtSubject>();
		
		if(subjectIds==null || subjectIds.isEmpty()) return result;
		
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();

		if(subjectIds.size()==1){
			hql = "from CmcCtSubject where subjectid=:subjectid";
			params.put("subjectid", subjectIds.get(0));
		}else{
			hql = "from CmcCtSubject where subjectid in(:subjectids)";
			params.put("subjectids", subjectIds);
		}		
		
		return (List<CmcCtSubject>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取题目列表
	 * @param libId
	 * @param baseAssigned
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtSubject> findSubject(Long libId, boolean baseAssigned, int limit){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtSubject t where t.libid=:libid and status=1");
		if(baseAssigned){
			sbHql.append(" and t.subjectid>t.cmcCtLib.assignedsubjectid");
		}
		sbHql.append(" order by t.subjectid");
		
		params.put("libid", libId);
		
		return (List<CmcCtSubject>)qryListTop(sbHql.toString(), params, limit);
	}
	
	/**
	 * 获取题目列表
	 * @param libId
	 * @param baseSubjectId
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtSubject> findSubject(Long libId, Long baseSubjectId, int limit){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcCtSubject t where t.libid=:libid and status=1");
		params.put("libid", libId);

		if(baseSubjectId!=null){
			sbHql.append(" and t.subjectid>:subjectid");
			params.put("subjectid", baseSubjectId);
		}
		sbHql.append(" order by t.subjectid");
		
		
		return (List<CmcCtSubject>)qryListTop(sbHql.toString(), params, limit);
	}
	
	/**
	 * 获取题目
	 * @param libId not null
	 * @param content not null
	 * @return
	 */
	public CmcCtSubject getSubject(Long libId, String content){
		String hql = "from CmcCtSubject t where t.libid=:libid and content=:content";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("libid", libId);
		params.put("content", content);
		
		return (CmcCtSubject)load(hql, params);
	}
	
	/**
	 * 获取题库题目选项列表
	 * @param libIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtOption> findOption(List<Long> libIds){
		String hql = "from CmcCtOption where libid in(:libids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("libids", libIds);
		
		return (List<CmcCtOption>) qryListByMap(hql, params);
	}
	
	/**
	 * 获取题目选项列表
	 * @param subjectIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtOption> findOptionBySubjectId(List<Long> subjectIds){
		String hql = "from CmcCtOption where subjectid in(:subjectids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subjectids", subjectIds);
		
		return (List<CmcCtOption>) qryListByMap(hql, params);
	}
	
	/**
	 * 更新上次分配的题目编号
	 * @param libId
	 * @param assignedSubjectId
	 */
	public void updateAssignedSubjectId(Long libId, Long assignedSubjectId){
		String hql = "update CmcCtLib set assignedsubjectid=:assignedsubjectid where libid=:libid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assignedsubjectid", assignedSubjectId);
		params.put("libid", libId);
		
		updateBatch(hql, params);
	}
}
