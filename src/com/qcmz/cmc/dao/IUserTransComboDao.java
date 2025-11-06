package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.entity.CmcUCombohis;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IUserTransComboDao extends IBaseDAO {
	/**
	 * 分页获取用户翻译套餐列表，带套餐
	 * @param map
	 * @param pageBean<CmcUCombo>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户有效的套餐列表
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	public List<CmcUCombo> findUserValidCombo(Long userId, String transType, Integer comboType);
	/**
	 * 获取用户套餐列表
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	public List<CmcUCombo> findUserCombo(Long userId, String transType, Integer comboType);
	/**
	 * 获取用户套餐列表
	 * @param ucids
	 * @return
	 */
	public List<CmcUCombo> findUserCombo(Set<Long> ucids);
	/**
	 * 获取用户套餐使用记录
	 * @param ucid
	 * @return
	 */
	public List<CmcUCombohis> findHis(Long ucid);
	/**
	 * 获取用户套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcUCombo getUserCombo(String orderId);
	/**
	 * 获取用户套餐信息
	 * @param exchangeCode
	 * @return
	 */
	public CmcUCombo getUserComboByCode(String exchangeCode);
	/**
	 * 获取用户翻译套餐信息，带套餐信息
	 * @param ucid
	 * @return
	 */
	public CmcUCombo getUserComboJoinCombo(Long ucid);
	/**
	 * 获取用户翻译套餐使用的已完成的订单数
	 * @param ucid not null
	 * @param operDate
	 * @return
	 */
	public Long getUserComboUsedCount(Long ucid, Date operDate);
	/**
	 * 创建用户翻译套餐序号
	 * @return
	 * 修改历史：
	 */
	public Long newUserComboSeq();
	/**
	 * 删除使用记录
	 * @param orderId
	 * @return
	 */
	public void deleteHis(String orderId);
}
