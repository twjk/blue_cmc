package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRpCash;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IRedPacketCashDao extends IBaseDAO {
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRpCash>
	 * 修改历史：
	 */
	public void findCash(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<CmcRpCash> findCash(Long userId, Integer status);
	/**
	 * 获取提现数
	 * @param status not null
	 * @return
	 */
	public Long getCashCount(Integer status);
}
