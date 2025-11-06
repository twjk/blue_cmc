package com.qcmz.cmc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.entity.CmcWalletBill;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.service.IWalletBillService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.framework.exception.BusinessException;

@Component
public class WalletConsumeServiceImpl implements IWalletConsumeService {
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IWalletBillService walletBillService;

	/**
	 * 钱包消费
	 * @param userId
	 * @param amount
	 * @param subServiceType
	 * @param orderId
	 * @exception BusinessException
	 */
	public void consume(Long userId, double amount, String subServiceType, String orderId){
		CmcWalletAccount account = walletAccountService.getAccount(userId);
		if(account.getBalance()<amount){
			throw new BusinessException("余额不足");
		}
		walletBillService.saveConsumeBill(userId, amount, subServiceType, orderId);
		walletAccountService.updateAmount(userId);
	}
	
	/**
	 * 钱包消费，不考虑余额
	 * @param userId
	 * @param amount
	 * @param subServiceType
	 * @param orderId
	 */
	public void consumeForce(Long userId, double amount, String subServiceType, String orderId){
		walletBillService.saveConsumeBill(userId, amount, subServiceType, orderId);
		walletAccountService.updateAmount(userId);
	}
	
	/**
	 * 退款
	 * @param userId
	 * @param amount
	 * @param orderId
	 */
	public boolean refund(Long userId, double amount, String orderId){
		CmcWalletBill consumeBill = walletBillService.getBill(userId, orderId, DictConstant.DICT_WALLET_BILLTYPE_CONSUME);
		if(consumeBill==null || Math.abs(consumeBill.getAmount())<amount){
			return false;
		}
		CmcWalletBill refundBill = walletBillService.getBill(userId, orderId, DictConstant.DICT_WALLET_BILLTYPE_CONSUMEREFUND);
		if(refundBill!=null) return true;
		
		walletBillService.saveConsumeRefundBill(userId, amount, consumeBill);
		walletAccountService.updateAmount(userId);
		return true;
	}
}
