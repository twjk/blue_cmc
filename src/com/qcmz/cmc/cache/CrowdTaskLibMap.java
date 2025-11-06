package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ICrowdTaskLibDao;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.NumberUtil;

/**
 * 众包任务题库缓存（只有前置任务的题库）
 */
@Component
public class CrowdTaskLibMap extends AbstractCacheMap {
	@Autowired
	private ICrowdTaskLibDao crowdTaskLibDao; 
	
	/**
	 * 题库题目缓存<libId, List<CmcCtSubject带options>>
	 */
	private Map<Long, List<CmcCtSubject>> libSubjectMap = null;
	/**
	 * 题目缓存<subjectId,CmcCtSubject带options>
	 */
	private Map<Long, CmcCtSubject> subjectMap = null;
	
	@Override
	@PostConstruct
	protected void init() {
		Map<Long, List<CmcCtSubject>> libSubjectMapTemp = new HashMap<Long, List<CmcCtSubject>>();
		Map<Long, CmcCtSubject> subjectMapTemp = new HashMap<Long, CmcCtSubject>();
		
		List<Long> preTasklibIds = crowdTaskLibDao.findLibIdOfPreTask();
		if(!preTasklibIds.isEmpty()){
			for (Long libId : preTasklibIds) {
				libSubjectMapTemp.put(libId, new ArrayList<CmcCtSubject>());
			}
			//题目
			List<CmcCtSubject> subjects = crowdTaskLibDao.findSubject(preTasklibIds);
			for (CmcCtSubject subject : subjects) {
				subjectMapTemp.put(subject.getSubjectid(), subject);
				if(SystemConstants.STATUS_ON.equals(subject.getStatus())){
					libSubjectMapTemp.get(subject.getLibid()).add(subject);
				}
			}
			//选项
			List<CmcCtOption> options = crowdTaskLibDao.findOption(preTasklibIds);
			for (CmcCtOption option : options) {
				subjectMapTemp.get(option.getSubjectid()).getOptions().add(option);
			}			
		}
		
		libSubjectMap = libSubjectMapTemp;
		subjectMap = subjectMapTemp;
	}

	/**
	 * 获取题目，带选项
	 * @param subjectId
	 * @return
	 */
	public CmcCtSubject getSubject(Long subjectId){
		if(safeInit(subjectMap)){
			return subjectMap.get(subjectId);
		}
		return null;
	}
	
	/**
	 * 从题库中获取有效的题目，带选项
	 * @param libId
	 * @return
	 */
	public CmcCtSubject getSubjectFromLib(Long libId){
		if(safeInit(libSubjectMap)){
			List<CmcCtSubject> libSubjects = libSubjectMap.get(libId);
			if(libSubjects==null || libSubjects.isEmpty()) return null;
			
			int subjectCount = libSubjects.size();
			//只有一个题目，直接返回
			if(subjectCount==1) return libSubjects.get(0);
			//多个题目，随机取一个返回
			return libSubjects.get(NumberUtil.random(subjectCount-1, 0));
		}
		return null;
	}
		
	/**
	 * 从题库中获取有效的题目，带选项
	 * @param libId
	 * @return
	 */
	public List<CmcCtSubject> findSubjectFromLib(Long libId, int subjectNum){
		List<CmcCtSubject> result = new ArrayList<CmcCtSubject>();
		if(safeInit(libSubjectMap)){
			List<CmcCtSubject> libSubjects = libSubjectMap.get(libId);
			if(libSubjects==null || libSubjects.isEmpty()) return result;
			
			int subjectCount = libSubjects.size();
			
			if(subjectCount==subjectNum) return libSubjects;
			
			int fromIndex = NumberUtil.random(subjectCount-1, 0);
			int toIndex = fromIndex+subjectNum;
			if(toIndex>subjectCount) toIndex = subjectCount;
			
			result.addAll(libSubjects.subList(fromIndex, toIndex));
			
			if(result.size()<subjectNum){
				result.addAll(libSubjects.subList(0, subjectNum-result.size()));
			}
		}
		return result;
	}
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}

}
