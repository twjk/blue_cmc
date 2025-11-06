package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRewardCash;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IRewardCashDao extends IBaseDAO {
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRewardCash>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取待打款的提现编号
	 * @param pageSize not null
	 * @param maxId not null
	 * @param lastId
	 * @return
	 */
	public List<Long> findCashId4Transfer(int pageSize, Long maxId, Long lastId);
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<CmcRewardCash> findCash(Long userId, Integer status);
	/**
	 * 获取提现信息，带cmcRewardAccount
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCashJoinAccount(Long cashId);
	/**
	 * 获取提现数
	 * @param status not null
	 * @return
	 */
	public Long getCashCount(Integer status);
}
