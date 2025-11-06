package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRedPacketBillDao;
import com.qcmz.cmc.entity.CmcRpBill;
import com.qcmz.cmc.service.IRedPacketBillService;

@Service
public class RedPacketBillServiceImpl implements IRedPacketBillService {
	@Autowired
	private IRedPacketBillDao redPacketBillDao;
	
	/**
	 * 添加收红包账单
	 * @param userId
	 * @param amount
	 * @param packetId
	 */
	public void saveReceiveBill(Long userId, Double amount, String packetId){
		CmcRpBill bill = new CmcRpBill();
		bill.setUserid(userId);
		bill.setBilltype(DictConstant.DICT_REDPACKET_BILLTYPE_RECEIVE);
		bill.setAmount(amount);
		bill.setPacketid(packetId);
		bill.setCreatetime(new Date());
		redPacketBillDao.save(bill);
	}
	
	/**
	 * 添加发红包账单
	 * @param userId
	 * @param amount
	 * @param packetId
	 */
	public void saveSendBill(Long userId, Double amount, String packetId){
		CmcRpBill bill = new CmcRpBill();
		bill.setUserid(userId);
		bill.setBilltype(DictConstant.DICT_REDPACKET_BILLTYPE_SEND);
		bill.setAmount(-amount);
		bill.setPacketid(packetId);
		bill.setCreatetime(new Date());
		redPacketBillDao.save(bill);		
	}
	
	/**
	 * 添加提现账单
	 * @param userId
	 * @param amount
	 * @param cashId
	 */
	public void saveCashBill(Long userId, Double amount, Long cashId){
		CmcRpBill bill = new CmcRpBill();
		bill.setUserid(userId);
		bill.setBilltype(DictConstant.DICT_REDPACKET_BILLTYPE_CASH);
		bill.setAmount(-amount);
		bill.setCashid(cashId);
		bill.setCreatetime(new Date());
		redPacketBillDao.save(bill);
	}
	
	/**
	 * 添加驳回提现申请账单
	 * @param userId
	 * @param amount
	 * @param cashId
	 */
	public void saveRejectCashBill(Long userId, Double amount, Long cashId){
		CmcRpBill bill = new CmcRpBill();
		bill.setUserid(userId);
		bill.setBilltype(DictConstant.DICT_REDPACKET_BILLTYPE_REJECTCASH);
		bill.setAmount(amount);
		bill.setCashid(cashId);
		bill.setCreatetime(new Date());
		redPacketBillDao.save(bill);
	}
	
	/**
	 * 添加红包未领取余额账单
	 * @param userId
	 * @param amount
	 * @param packetId
	 */
	public void saveUnReceiveBill(Long userId, Double amount, String packetId){
		CmcRpBill bill = new CmcRpBill();
		bill.setUserid(userId);
		bill.setBilltype(DictConstant.DICT_REDPACKET_BILLTYPE_UNRECEIVE);
		bill.setAmount(amount);
		bill.setPacketid(packetId);
		bill.setCreatetime(new Date());
		redPacketBillDao.save(bill);
	}
}
