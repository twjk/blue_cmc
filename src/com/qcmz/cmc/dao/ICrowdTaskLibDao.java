package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtLib;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.vo.CrowdTaskLibLackBean;
import com.qcmz.framework.dao.IBaseDAO;

public interface ICrowdTaskLibDao extends IBaseDAO {
	/**
	 * 获取前置任务题库编号列表
	 * @return
	 */
	public List<Long> findLibIdOfPreTask();
	/**
	 * 获取题目不足的题库
	 * @return
	 */
	public List<CrowdTaskLibLackBean> findLib4Lack();
	/**
	 * 根据题库名获取题库
	 * @param libName
	 * @return
	 */
	public CmcCtLib getLibByName(String libName);
	/**
	 * 获取题库题目数
	 * @param libId
	 * @return
	 */
	public Long getSubjectCount(Long libId);
	/**
	 * 获取题库题目列表
	 * @param libIds
	 * @return
	 */
	public List<CmcCtSubject> findSubject(List<Long> libIds);
	/**
	 * 获取题目列表
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtSubject> findSubjectBySubjectId(List<Long> subjectIds);
	/**
	 * 获取题目列表
	 * @param libId
	 * @param baseAssigned
	 * @param limit
	 * @return
	 */
	public List<CmcCtSubject> findSubject(Long libId, boolean baseAssigned, int limit);
	/**
	 * 获取题目列表
	 * @param libId
	 * @param baseSubjectId
	 * @param limit
	 * @return
	 */
	public List<CmcCtSubject> findSubject(Long libId, Long baseSubjectId, int limit);
	/**
	 * 获取题目
	 * @param libId not null
	 * @param content not null
	 * @return
	 */
	public CmcCtSubject getSubject(Long libId, String content);
	/**
	 * 获取题库题目选项列表
	 * @param libIds
	 * @return
	 */
	public List<CmcCtOption> findOption(List<Long> libIds);
	/**
	 * 获取题目选项列表
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtOption> findOptionBySubjectId(List<Long> subjectIds);
	/**
	 * 更新上次分配的题目编号
	 * @param libId
	 * @param assignedSubjectId
	 */
	public void updateAssignedSubjectId(Long libId, Long assignedSubjectId);
}
