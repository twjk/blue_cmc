package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRedPacketAccountDao;
import com.qcmz.cmc.dao.IRedPacketCashDao;
import com.qcmz.cmc.entity.CmcRpCash;
import com.qcmz.cmc.service.IRedPacketAccountService;
import com.qcmz.cmc.service.IRedPacketBillService;
import com.qcmz.cmc.service.IRedPacketCashService;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;

@Service
public class RedPacketCashServiceImpl implements IRedPacketCashService {
	@Autowired
	private IRedPacketCashDao redPacketCashDao;
	@Autowired
	private IRedPacketAccountDao redPacketAccountDao;
	
	@Autowired
	private IRedPacketAccountService redPacketAccountService;
	@Autowired
	private IRedPacketBillService redPacketBillService;
	
	
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRpCash>
	 * 修改历史：
	 */
	public void findCash(Map<String, String> map, PageBean pageBean){
		redPacketCashDao.findCash(map, pageBean);
	}
	
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<CmcRpCash> findCash(Long userId){
		return redPacketCashDao.findCash(userId, null);
	}
	
	/**
	 * 获取提现信息
	 * @param cashId
	 * @return
	 */
	public CmcRpCash getCash(Long cashId){
		return (CmcRpCash) redPacketCashDao.load(CmcRpCash.class, cashId);
	}
	
	/**
	 * 获取提现信息，含用户、帐户
	 * @param cashId
	 * @return
	 */
	public CmcRpCash getCashJoin(Long cashId){
		CmcRpCash entity = getCash(cashId);
		if(entity!=null){
			entity.setAccount(redPacketAccountService.getAccount(entity.getUserid()));
			entity.setUser(UserMap.getUser(entity.getUserid()));
		}
		return entity;
	}
	
	/**
	 * 申请提现
	 * @param userId
	 * @param cashAmount
	 */
	public void applyCash(Long userId, Double cashAmount){
		CmcRpCash cash = new CmcRpCash();
		cash.setUserid(userId);
		cash.setCashamount(cashAmount);
		cash.setCreatetime(new Date());
		cash.setStatus(DictConstant.DICT_REDPACKETCASH_STATUS_APPLY);
		redPacketCashDao.save(cash);
		
		redPacketBillService.saveCashBill(userId, cashAmount, cash.getCashid());
		
		redPacketAccountDao.updateAccount(userId);
	}
	
	/**
	 * 成功提现
	 * @param cashId
	 * @param oper
	 */
	public void successCash(Long cashId, String outTradeId, Date cashTime, Operator oper){
		CmcRpCash cash = getCash(cashId);
		cash.setStatus(DictConstant.DICT_REDPACKETCASH_STATUS_CASHED);
		cash.setOuttradeid(outTradeId);
		cash.setCashtime(cashTime);
		cash.setDealcd(oper.getOperCd());
		cash.setDealname(oper.getOperName());
		cash.setDealtime(new Date());
		redPacketCashDao.update(cash);
	}
	
	/**
	 * 驳回提现申请
	 */
	public void rejectCash(Long cashId, String reason, Operator oper){
		CmcRpCash cash = getCash(cashId);
		cash.setStatus(DictConstant.DICT_REDPACKETCASH_STATUS_REJECT);
		cash.setDealcd(oper.getOperCd());
		cash.setDealname(oper.getOperName());
		cash.setDealtime(new Date());
		cash.setDealresult(reason);
		redPacketCashDao.update(cash);
		
		//账单
		redPacketBillService.saveRejectCashBill(cash.getUserid(), cash.getCashamount(), cash.getCashid());
		
		//更新帐户
		redPacketAccountDao.updateAccount(cash.getUserid());
	}
}
