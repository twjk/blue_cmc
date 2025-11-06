package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcFeeAccount;
import com.qcmz.cmc.ws.provide.vo.AccountBean;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IAccountService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取帐户信息
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeAccount loadAccount(Long userId);
	/**
	 * 获取帐户信息
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public AccountBean getAccountInfo(Long userId);
	/**
	 * 添加或修改
	 * @param bean
	 * 修改历史：
	 */
	public void saveOrUpdate(CmcFeeAccount bean);
}
