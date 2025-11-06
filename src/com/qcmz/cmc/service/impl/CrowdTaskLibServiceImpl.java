package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ICrowdTaskLibDao;
import com.qcmz.cmc.entity.CmcCtLib;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.vo.CrowdTaskLibLackBean;

/**
 * 众包任务题库
 */
@Service
public class CrowdTaskLibServiceImpl implements ICrowdTaskLibService {
	@Autowired
	private ICrowdTaskLibDao crowdTaskLibDao;
	
	/**
	 * 获取题目不足的题库
	 * @return
	 */
	public List<CrowdTaskLibLackBean> findLib4Lack(){
		return crowdTaskLibDao.findLib4Lack();
	}
	
	/**
	 * 根据题库名获取题库
	 * @param libName
	 * @return
	 */
	public CmcCtLib getLibByName(String libName){
		return crowdTaskLibDao.getLibByName(libName);
	}
	
	/**
	 * 获取题库题目数
	 * @param libId
	 * @return
	 */
	public Long getSubjectCount(Long libId){
		return crowdTaskLibDao.getSubjectCount(libId);
	}
	
	/**
	 * 保存题库
	 * @param libName
	 */
	public CmcCtLib saveLib(String libName){
		CmcCtLib lib = new CmcCtLib();
		lib.setLibname(libName);
		crowdTaskLibDao.saveOrUpdate(lib);
		return lib;
	}
	
	/**
	 * 保存题目
	 * @param subject
	 */
	public void saveSubject(CmcCtSubject subject){
		crowdTaskLibDao.save(subject);
	}
	
	/**
	 * 修改题目
	 * @param subject
	 */
	public void updateSubject(CmcCtSubject subject){
		crowdTaskLibDao.update(subject);
	}
	
	/**
	 * 批量保存题目
	 * @param list
	 */
	public void saveOrUpdateAll(List<CmcCtSubject> list){
		if(list==null || list.isEmpty()) return;
		
		crowdTaskLibDao.saveOrUpdateAll(list);

		List<CmcCtOption> optionList = new ArrayList<CmcCtOption>();
		for (CmcCtSubject subject : list) {
			for (CmcCtOption option : subject.getOptions()) {
				option.setSubjectid(subject.getSubjectid());
				optionList.add(option);
			}
		}
		if(!optionList.isEmpty()){
			crowdTaskLibDao.saveOrUpdateAll(optionList);
		}
	}
	
	/**
	 * 获取题目列表
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtSubject> findSubjectBySubjectId(List<Long> subjectIds){
		return crowdTaskLibDao.findSubjectBySubjectId(subjectIds);
	}
	
	/**
	 * 获取题目列表，带选项
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtSubject> findSubjectBySubjectIdJoin(List<Long> subjectIds){
		List<CmcCtSubject> result = crowdTaskLibDao.findSubjectBySubjectId(subjectIds);
		if(result.isEmpty()) return result;
		
		Map<Long, CmcCtSubject> subjectMap = new HashMap<Long, CmcCtSubject>();
		for (CmcCtSubject subject : result) {
			subjectMap.put(subject.getSubjectid(), subject);
		}
		
		List<CmcCtOption> options = findOptionBySubjectId(subjectIds);
		for (CmcCtOption option : options) {
			subjectMap.get(option.getSubjectid()).getOptions().add(option);
		}
		
		return result;
	}
	
	
	/**
	 * 获取题目列表
	 * @param libId
	 * @param baseAssigned
	 * @param limit
	 * @return
	 */
	public List<CmcCtSubject> findSubject(Long libId, boolean baseAssigned, int limit){
		return crowdTaskLibDao.findSubject(libId, baseAssigned, limit);
	}
	
	/**
	 * 获取题目列表
	 * @param libId
	 * @param baseSubjectId
	 * @param limit
	 * @return
	 */
	public List<CmcCtSubject> findSubject(Long libId, Long baseSubjectId, int limit){
		return crowdTaskLibDao.findSubject(libId, baseSubjectId, limit);
	}
	
	/**
	 * 获取题目信息
	 * @param subjectId
	 * @return
	 */
	public CmcCtSubject getSubject(Long subjectId){
		return (CmcCtSubject) crowdTaskLibDao.load(CmcCtSubject.class, subjectId);
	}
	
	/**
	 * 获取题目
	 * @param libId not null
	 * @param content not null
	 * @return
	 */
	public CmcCtSubject getSubject(Long libId, String content){
		return crowdTaskLibDao.getSubject(libId, content);
	}
	
	/**
	 * 获取题目选项列表
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtOption> findOptionBySubjectId(List<Long> subjectIds){
		return crowdTaskLibDao.findOptionBySubjectId(subjectIds);
	}
	
	/**
	 * 更新上次分配的题目编号
	 * @param libId
	 * @param assignedSubjectId
	 */
	public void updateAssignedSubjectId(Long libId, Long assignedSubjectId){
		crowdTaskLibDao.updateAssignedSubjectId(libId, assignedSubjectId);
	}
}
