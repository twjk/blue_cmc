package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IUserTransComboService {
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
	 * 获取用户套餐信息
	 * @param ucid
	 * @return
	 */
	public CmcUCombo getUserCombo(Long ucid);
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
	 * 获取用户套餐信息
	 * @param ucid
	 * @return
	 */
	public CmcUCombo getUserComboJoin(Long ucid);
	/**
	 * 获取用户翻译套餐使用的已完成的订单数
	 * @param ucid not null
	 * @param operDate
	 * @return
	 */
	public Long getUserComboUsedCount(Long ucid, Date operDate);
	/**
	 * 保存用户套餐信息
	 * @param cmcUCombo
	 */
	public void saveOrUpdateUserCombo(CmcUCombo cmcUCombo);
	/**
	 * 添加用户套餐
	 * @param combo
	 * @param pkg
	 * @param userId
	 * @param num
	 * @param startTime
	 * @param orderId
	 * @param paySide
	 * @param copies 批量添加份数
	 */
	public List<CmcUCombo> addUserCombo(CmcCombo combo, CmcComboPackage pkg, Long userId, Integer num, Date startTime, String orderId, Integer paySide, int copies);
	/**
	 * 用户使用用户套餐
	 * @param ucid
	 * @param userId
	 * @param orderId
	 * @exception DataException
	 */
	public void useUserCombo4User(Long ucid, Long userId, String transType, String orderId);
	/**
	 * 操作员录入用户套餐使用记录
	 * @param ucid
	 * @param usedTime
	 * @param orderId
	 * @param remark
	 * @param oper
	 * @exception DataException
	 */
	public void useUserCombo4Operator(Long ucid, Date usedTime, String orderId, String remark, Operator oper);
	
	/**
	 * 回滚用户套餐使用
	 * @param ucid
	 * @param orderId
	 */
	public void rollbackUserCombo(Long ucid, String orderId);
	/**
	 * 作废用户套餐
	 * @param ucid
	 * @param oper
	 * @exception DataAccessException
	 */
	public void invalidUserCombo(Long ucid, Operator oper);
	/**
	 * 删除用户套餐
	 * @param ucid
	 * @param oper
	 * @exception DataAccessException
	 */
	public void deleteUserCombo(Long ucid, Operator oper);
}
