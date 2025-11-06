package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRewardBillDao;
import com.qcmz.cmc.entity.CmcRewardBill;
import com.qcmz.cmc.service.IRewardBillService;
import com.qcmz.cmc.ws.provide.vo.RewardBillBean;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;

@Service
public class RewardBillServiceImpl implements IRewardBillService {
	@Autowired
	private IRewardBillDao rewardBillDao;
	
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcRewardBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		rewardBillDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 分页获取用户账单列表
	 * @param query
	 * @return
	 */
	public List<RewardBillBean> findBillInfo(RewardBillQueryBean query){
		return rewardBillDao.findBillInfo(query);
	}
	
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param subServiceType
	 * @param date 日期
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, String subServiceType, Date date){
		return rewardBillDao.getCount(userId, billType, subServiceType, date);
	}
	
	/**
	 * 是否存在
	 * @param userId
	 * @param billType
	 * @param subServiceType
	 * @param orderId
	 * @return
	 */
	public Boolean isExist(Long userId, Integer billType, String subServiceType, String orderId){
		return rewardBillDao.isExist(userId, billType, subServiceType, orderId);
	}
	
	/**
	 * 添加获得奖励账单
	 * @param userId
	 * @param amount
	 * @param billDesc
	 * @param orderId
	 */
	public void saveReceiveBill(Long userId, Double amount, String billDesc, String subServiceType, String orderId){
		CmcRewardBill bill = new CmcRewardBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.DICT_INCEXP_INCOME);
		bill.setBilltype(DictConstant.DICT_REWARD_BILLTYPE_RECEIVE);
		bill.setAmount(amount);
		bill.setBilldesc(billDesc);
		bill.setSubservicetype(subServiceType);
		bill.setOrderid(orderId);
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		rewardBillDao.save(bill);
	}
	
	/**
	 * 添加赠送账单
	 * @param userId
	 * @param amount
	 * @param billDesc
	 */
	public void saveBestowBill(Long userId, Double amount, String billDesc){
		CmcRewardBill bill = new CmcRewardBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.DICT_INCEXP_INCOME);
		bill.setBilltype(DictConstant.DICT_REWARD_BILLTYPE_BESTOW);
		bill.setAmount(amount);
		bill.setBilldesc(billDesc);
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		rewardBillDao.save(bill);
	}
	
	/**
	 * 添加提现账单
	 * @param userId
	 * @param amount
	 * @param cashId
	 */
	public void saveCashBill(Long userId, Double amount, Long cashId){
		CmcRewardBill bill = new CmcRewardBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.DICT_INCEXP_EXPENSES);
		bill.setBilltype(DictConstant.DICT_REWARD_BILLTYPE_CASH);
		bill.setAmount(-amount);
		bill.setBilldesc("提现");
		bill.setOrderid(String.valueOf(cashId));
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		rewardBillDao.save(bill);
	}
	
	/**
	 * 添加驳回提现申请账单
	 * @param userId
	 * @param amount
	 * @param cashId
	 */
	public void saveRejectCashBill(Long userId, Double amount, Long cashId){
		CmcRewardBill bill = new CmcRewardBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.DICT_INCEXP_INCOME);
		bill.setBilltype(DictConstant.DICT_REWARD_BILLTYPE_REJECTCASH);
		bill.setAmount(amount);
		bill.setBilldesc("提现失败");
		bill.setOrderid(String.valueOf(cashId));
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		rewardBillDao.save(bill);
	}
}
