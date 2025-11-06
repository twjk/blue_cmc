package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILocalTaskPicDao;
import com.qcmz.cmc.entity.CmcLtPic;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class LocalTaskPicDao extends BaseDAO implements ILocalTaskPicDao {
	/**
	 * 获取任务图片列表
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtPic> findPic(Long taskId){
		String hql = "from CmcLtPic where taskid=:taskid order by sortindex, picid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		
		return (List<CmcLtPic>) qryListByMap(hql, params);
	}
	
	/**
	 * 清除任务图片
	 * @param taskId
	 */
	public void clearPic(Long taskId){
		String hql = "delete from CmcLtPic where taskid=:taskid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskid", taskId);
		
		deleteByMap(hql, params);
	}
}
