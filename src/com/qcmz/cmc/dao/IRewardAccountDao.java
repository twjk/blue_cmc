package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IRewardAccountDao extends IBaseDAO {
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcRewardAccount>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 更新帐户金额
	 * @param userId
	 */
	public void updateAmount(Long userId);
	/**
	 * 更新帐户状态
	 * @param userId
	 * @param status
	 */
	public void updateStatus(Long userId, Integer status);
}
