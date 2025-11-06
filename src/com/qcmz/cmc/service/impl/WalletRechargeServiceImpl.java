package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IWalletRechargeDao;
import com.qcmz.cmc.entity.CmcWalletRecharge;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.service.IWalletBillService;
import com.qcmz.cmc.service.IWalletRechargeService;
import com.qcmz.cmc.vo.WalletOfflineRechargeBean;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class WalletRechargeServiceImpl implements IWalletRechargeService {
	@Autowired
	private IWalletRechargeDao walletRechargeDao;
	
	@Autowired
	private IWalletBillService walletBillService;
	@Autowired
	private IWalletAccountService walletAccountService;
	
	/**
	 * 分页获取充值列表，带帐户
	 * @param map
	 * @param pageBean<CmcWalletRecharge>
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		walletRechargeDao.queryByMapTerm(map, pageBean);
		List<CmcWalletRecharge> list = (List<CmcWalletRecharge>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcWalletRecharge recharge : list) {
			userIds.add(recharge.getUserid());
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcWalletRecharge recharge : list) {
			recharge.setUser(userMap.get(recharge.getUserid()));
		}
	}
	
	/**
	 * 获取充值信息
	 * @param orderId
	 * @return
	 */
	public CmcWalletRecharge getRecharge(String orderId){
		return (CmcWalletRecharge) walletRechargeDao.load(CmcWalletRecharge.class, orderId);
	}
	
	/**
	 * 获取充值信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcWalletRecharge getRecharge(String orderId, Long userId){
		CmcWalletRecharge entity = getRecharge(orderId);
		if(entity!=null && !entity.getUserid().equals(userId)){
			entity = null;
		}
		return entity;
	}

	/**
	 * 获取充值信息，带帐户
	 * @param orderId
	 * @return
	 */
	public CmcWalletRecharge getRechargeJoinAccount(String orderId){
		return (CmcWalletRecharge) walletRechargeDao.getRechargeJoinAccount(orderId);
	}
	
	/**
	 * 获取充值信息，带帐户
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcWalletRecharge getRechargeJoinAccount(String orderId, Long userId){
		CmcWalletRecharge entity = getRechargeJoinAccount(orderId);
		if(entity!=null && !entity.getUserid().equals(userId)){
			entity = null;
		}
		return entity;
	}
	
	/**
	 * 创建充值订单
	 * @param orderId
	 * @param bean
	 * @param version
	 * @return
	 */
	public CmcWalletRecharge createRecharge(String orderId, WalletRechargeCreateBean bean, String platform, String version){
		CmcWalletRecharge entity = new CmcWalletRecharge();
		entity.setOrderid(orderId);
		entity.setUserid(bean.getUid());
		entity.setAmount(bean.getAmount());
		entity.setPayableamount(bean.getAmount());
		entity.setIapid("walletRecharge"+entity.getPayableamount().intValue()+"rmb");
		entity.setCreatetime(new Date());
		entity.setPlatform(platform);
		entity.setVersion(version);
		entity.setStatus(DictConstant.DICT_WALLET_RECHARGESTATUS_WAITPAY);
		walletRechargeDao.save(entity);
		return entity;
	}
	
	/**
	 * 取消充值
	 * @param orderId
	 */
	public void cancelRecharge(String orderId){
		walletRechargeDao.updateStatus(orderId, DictConstant.DICT_WALLET_RECHARGESTATUS_CANCEL);
	}
	
	/**
	 * 支付成功
	 * @param charge
	 */
	public void paySuccess(CmcWalletRecharge charge){
		walletRechargeDao.update(charge);
		
		//账单
		walletBillService.saveRechargeBill(charge.getUserid(), charge.getAmount(), charge.getOrderid());
				
		//帐户余额
		walletAccountService.updateAmount(charge.getUserid());
	}

	/**
	 * 手工充值
	 * @param bean
	 * @param operator
	 */
	public void offlineRecharge(WalletOfflineRechargeBean bean, Operator operator){
		//入账
		walletBillService.saveBill(bean.getUserId(), bean.getAmount()
				, bean.getBillType(), bean.getOrderId(), bean.getDescription()
				, operator.getOperCd(), operator.getOperName());
		
		//帐户余额
		walletAccountService.updateAmount(bean.getUserId());
	}
	
	/**
	 * 退款成功
	 */
	public void refundSuccess(CmcWalletRecharge order){
		walletRechargeDao.update(order);
		
		//账单
		walletBillService.saveRechargeRefundBill(order.getUserid(), order.getAmount(), order.getOrderid());
		
		//帐户余额
		walletAccountService.updateAmount(order.getUserid());
	}
}
