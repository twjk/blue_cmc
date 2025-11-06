package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcLtSub;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ILocalTaskSubDao extends IBaseDAO {
	/**
	 * 分页查询用户订阅
	 * @param map
	 * @param pageBean<CmcLtSub>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户订阅
	 * @param userId
	 * @return
	 */
	public CmcLtSub getSubByUserId(Long userId);
	/**
	 * 分页获取待通知的订阅
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CmcLtSub> findSub4Notify(int pageSize, Long lastId);
	/**
	 * 更新通知时间为当前时间
	 * @param subIds
	 */
	public void updateNotifyTime(List<Long> subIds);
	/**
	 * 删除用户订阅
	 * @param userId
	 */
	public void deleteSubByUserId(Long userId);
}
