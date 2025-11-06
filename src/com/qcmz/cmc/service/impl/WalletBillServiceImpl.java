package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IWalletBillDao;
import com.qcmz.cmc.entity.CmcWalletBill;
import com.qcmz.cmc.service.IWalletBillService;
import com.qcmz.cmc.util.WalletUtil;
import com.qcmz.cmc.ws.provide.vo.WalletBillBean;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;

@Service
public class WalletBillServiceImpl implements IWalletBillService {
	@Autowired
	private IWalletBillDao walletBillDao;
	
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcWalletBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		walletBillDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 分页获取账单列表
	 * @param query
	 * @return
	 */
	public List<WalletBillBean> findBillInfo(WalletBillQueryBean query){
		return walletBillDao.findBillInfo(query);
	}
	
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param date
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, Date date){
		return walletBillDao.getCount(userId, billType, date);
	}
	
	/**
	 * 获取用户账单
	 * @param userId not null
	 * @param orderId not null
	 * @param billType not null
	 */
	public CmcWalletBill getBill(Long userId, String orderId, Integer billType){
		return walletBillDao.getBill(userId, orderId, billType);
	}
	
	/**
	 * 保存充值账单
	 * @param userId
	 * @param amount
	 * @param orderId
	 */
	public void saveRechargeBill(Long userId, double amount, String orderId){
		saveBill(userId, amount, DictConstant.DICT_WALLET_BILLTYPE_RECHARGE, orderId, null, null, null);
	}
		
	/**
	 * 保存消费账单
	 * @param userId
	 * @param amount
	 * @param subServiceType
	 * @param orderId
	 */
	public void saveConsumeBill(Long userId, double amount, String subServiceType, String orderId){
		CmcWalletBill bill = new CmcWalletBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.DICT_INCEXP_EXPENSES);
		bill.setBilltype(DictConstant.DICT_WALLET_BILLTYPE_CONSUME);
		bill.setAmount(-amount);
		bill.setBilldesc(DictMap.getSubServiceTypeMean(subServiceType)+"-"+orderId);
		bill.setSubservicetype(subServiceType);
		bill.setOrderid(orderId);
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		walletBillDao.save(bill);
	}
	
	/**
	 * 保存消费退款账单
	 * @param userId
	 * @param amount
	 * @param consumeBill
	 */
	public void saveConsumeRefundBill(Long userId, double amount, CmcWalletBill consumeBill){
		CmcWalletBill bill = new CmcWalletBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.DICT_INCEXP_INCOME);
		bill.setBilltype(DictConstant.DICT_WALLET_BILLTYPE_CONSUMEREFUND);
		bill.setAmount(amount);
		bill.setBilldesc(consumeBill.getBilldesc());
		bill.setSubservicetype(consumeBill.getSubservicetype());
		bill.setOrderid(consumeBill.getOrderid());
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		walletBillDao.save(bill);
	}
	
	/**
	 * 保存充值退款账单
	 * @param userId
	 * @param amount
	 * @param orderId
	 */
	public void saveRechargeRefundBill(Long userId, double amount, String orderId){
		saveBill(userId, -amount, DictConstant.DICT_WALLET_BILLTYPE_RECHARGEREFUND, orderId, null, null, null);		
	}
		
	/**
	 * 保存账单
	 * @param userId
	 * @param amount
	 * @param billType
	 * @param orderId
	 * @param description
	 * @param operator
	 * @param operatorName
	 */
	public void saveBill(Long userId, double amount, Integer billType, String orderId, String description, String operator, String operatorName){
		CmcWalletBill bill = new CmcWalletBill();
		bill.setUserid(userId);
		bill.setIncexp(DictConstant.getIncexpByWalletBillType(billType));
		bill.setBilltype(billType);
		bill.setAmount(amount);
		bill.setBilldesc(WalletUtil.getBillDesc(bill.getBilltype(), orderId, description));
		bill.setOrderid(orderId);
		bill.setCreatetime(new Date());
		bill.setCreatedate(DateUtil.truncDate(bill.getCreatetime()));
		bill.setCreatecd(operator);
		bill.setCreatename(operatorName);
		walletBillDao.save(bill);
	}
}
