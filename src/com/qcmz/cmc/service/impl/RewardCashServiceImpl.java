package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IRewardCashDao;
import com.qcmz.cmc.entity.CmcRewardCash;
import com.qcmz.cmc.service.IRewardAccountService;
import com.qcmz.cmc.service.IRewardBillService;
import com.qcmz.cmc.service.IRewardCashService;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class RewardCashServiceImpl implements IRewardCashService {
	@Autowired
	private IRewardCashDao rewardCashDao;
	@Autowired
	private IRewardBillService rewardBillService;
	@Autowired
	private IRewardAccountService rewardAccountService;
	
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRewardCash>
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		rewardCashDao.queryByMapTerm(map, pageBean);
		
		List<CmcRewardCash> list = (List<CmcRewardCash>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRewardCash cash : list) {
			userIds.add(cash.getUserid());			
		}
		
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRewardCash cash : list) {
			cash.setUser(userMap.get(cash.getUserid()));
		}
	}
	
	/**
	 * 分页获取待打款的提现编号
	 * @param pageSize not null
	 * @param maxId not null
	 * @param lastId
	 * @return
	 */
	public List<Long> findCashId4Transfer(int pageSize, Long maxId, Long lastId){
		return rewardCashDao.findCashId4Transfer(pageSize, maxId, lastId);
	}
	
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @return
	 */
	public List<CmcRewardCash> findCash(Long userId){
		return rewardCashDao.findCash(userId, null);
	}
	
	/**
	 * 获取提现信息
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCash(Long cashId){
		return (CmcRewardCash) rewardCashDao.load(CmcRewardCash.class, cashId);
	}
	
	/**
	 * 获取提现信息，带cmcRewardAccount
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCashJoinAccount(Long cashId){
		return rewardCashDao.getCashJoinAccount(cashId);
	}
	
	/**
	 * 获取提现信息，含用户、帐户
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCashJoin(Long cashId){
		CmcRewardCash entity = getCashJoinAccount(cashId);
		if(entity!=null){
			entity.setUser(UserMap.getUser(entity.getUserid()));
		}
		return entity;
	}
	
	/**
	 * 获取提现数
	 * @param status not null
	 * @return
	 */
	public Long getCashCount(Integer status){
		return rewardCashDao.getCashCount(status);
	}
	
	/**
	 * 更新
	 * @param entity
	 */
	public void update(CmcRewardCash entity){
		rewardCashDao.update(entity);
	}
	
	/**
	 * 申请提现
	 * @param userId
	 * @param cashAmount
	 */
	public Long applyCash(Long userId, Double cashAmount, String cashAccountType, String cashAccount){
		CmcRewardCash cash = new CmcRewardCash();
		cash.setUserid(userId);
		cash.setCashamount(cashAmount);
		cash.setAccounttype(cashAccountType);
		cash.setAccount(cashAccount);
		cash.setCreatetime(new Date());
		cash.setStatus(DictConstants.DICT_CASHSTATUS_APPLY);
		rewardCashDao.save(cash);
		
		rewardBillService.saveCashBill(userId, cashAmount, cash.getCashid());
		
		rewardAccountService.updateAmount(userId);
		
		return cash.getCashid();
	}
	
	/**
	 * 驳回提现申请
	 * @param cashId
	 * @param reason
	 * @param oper
	 */
	public void rejectCash(Long cashId, String reason, Operator oper){
		CmcRewardCash cash = getCash(cashId);
		cash.setStatus(DictConstants.DICT_CASHSTATUS_FAIL);
		cash.setDealcd(oper.getOperCd());
		cash.setDealname(oper.getOperName());
		cash.setDealtime(new Date());
		cash.setDealresult(reason);
		rewardCashDao.update(cash);
		
		//账单
		rewardBillService.saveRejectCashBill(cash.getUserid(), cash.getCashamount(), cash.getCashid());
		
		//更新帐户
		rewardAccountService.updateAmount(cash.getUserid());
	}
}
