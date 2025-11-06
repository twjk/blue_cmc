package com.qcmz.cmc.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IAccountDao;
import com.qcmz.cmc.entity.CmcFeeAccount;
import com.qcmz.cmc.service.IAccountService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.AccountBean;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountDao accountDao;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		accountDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取帐户信息
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeAccount loadAccount(Long userId){
		return (CmcFeeAccount) accountDao.load(CmcFeeAccount.class, userId);
	}
	
	/**
	 * 获取帐户信息
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public AccountBean getAccountInfo(Long userId){
		AccountBean bean = new AccountBean();
		
		//帐户信息
		CmcFeeAccount entity = loadAccount(userId);
		if(entity!=null){
			bean = BeanConvertUtil.toAccountBean(entity);
		}else{
			bean.setUserId(userId);
		}
		
		return bean;
	}
	
	/**
	 * 添加或修改
	 * @param bean
	 * 修改历史：
	 */
	public void saveOrUpdate(CmcFeeAccount bean){
		accountDao.saveOrUpdate(bean);
	}
}
