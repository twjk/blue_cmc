package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITranslatorCommissionDao;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.ITranslatorCommissionService;
import com.qcmz.cmc.vo.TranslatorCommissionBean;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class TranslatorCommissionServiceImpl implements ITranslatorCommissionService {
	@Autowired
	private ITranslatorCommissionDao translatorCommissionDao;
	
	/**
	 * 获取译员订单佣金统计列表
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 * @param operCd
	 * @return
	 */
	public List<TranslatorCommissionBean> findCommission(Date startDate, Date endDate, String operCd){
		return translatorCommissionDao.findCommission(startDate, endDate, operCd);
	}
		
	/**
	 * 获取已处理的订单列表，带使用的用户套餐信息
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 * @param commissionStatus
	 * @param operCd
	 * @return
	 */
	public List<CmcROrder> findDealtOrder(Date startDate, Date endDate, Integer commissionStatus, String operCd){
		return translatorCommissionDao.findDealtOrder(startDate, endDate, commissionStatus, operCd);
	}
	
	/**
	 * 获取译员佣金信息
	 * @param startDate
	 * @param endDate
	 * @param operCd
	 * @return
	 */
	public TranslatorCommissionBean getCommission(Date startDate, Date endDate, String operCd){
		TranslatorCommissionBean result = new TranslatorCommissionBean();

		List<CmcROrder> orderList = translatorCommissionDao.findDealtOrder(startDate, endDate, null, operCd);
		if(!orderList.isEmpty()){
			//计算金额
			Double orderAmount = 0.0;
			Double commissionAmount = 0.0;
			String operName = null;
			Set<Long> userIds = new HashSet<Long>();
			for (CmcROrder order : orderList) {
				orderAmount = DoubleUtil.add(orderAmount, order.getAmount());
				if(order.getCommissionamount()!=null){
					commissionAmount = DoubleUtil.add(commissionAmount, order.getCommissionamount());
				}
				operName = order.getOpername();
				userIds.add(order.getUserid());
			}
			
			//获取用户信息
			Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
			for (CmcROrder order : orderList) {
				order.setUser(userMap.get(order.getUserid()));
			}
			
			//组装
			result.setOperCd(operCd);
			result.setOperName(operName);
			result.setOrderAmount(orderAmount);
			result.setCommissionAmount(commissionAmount);
			result.setOrderCount(Long.valueOf(orderList.size()));
			result.setOrderList(orderList);
		}
		
		return result;
	}
}
