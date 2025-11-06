package com.qcmz.cmc.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILocalTaskSubDao;
import com.qcmz.cmc.entity.CmcLtSub;
import com.qcmz.cmc.service.ILocalTaskSubService;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class LocalTaskSubServiceImpl implements ILocalTaskSubService {
	@Autowired
	private ILocalTaskSubDao localTaskSubDao;
	
	/**
	 * 分页查询用户订阅
	 * @param map
	 * @param pageBean<CmcLtSub>
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		localTaskSubDao.queryByMapTerm(map, pageBean);
		
		List<CmcLtSub> list = (List<CmcLtSub>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcLtSub entity : list) {
			userIds.add(entity.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcLtSub entity : list) {
			entity.setUser(userMap.get(entity.getUserid()));
		}
	}
	
	/**
	 * 分页获取待通知的订阅
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CmcLtSub> findSub4Notify(int pageSize, Long lastId){
		return localTaskSubDao.findSub4Notify(pageSize, lastId);
	}
	
	/**
	 * 获取用户订阅
	 * @param userId
	 * @return
	 */
	public CmcLtSub getSubByUserId(Long userId){
		return localTaskSubDao.getSubByUserId(userId);
	}
	
	/**
	 * 保存
	 * @param entity
	 */
	public void saveOrUpdate(CmcLtSub entity){
		localTaskSubDao.saveOrUpdate(entity);
	}
	
	/**
	 * 更新通知时间为当前时间
	 * @param subIds
	 */
	public void updateNotifyTime(List<Long> subIds){
		localTaskSubDao.updateNotifyTime(subIds);
	}
	
	/**
	 * 删除用户订阅
	 * @param userId
	 */
	public void deleteSubByUserId(Long userId){
		localTaskSubDao.deleteSubByUserId(userId);
	}
}
