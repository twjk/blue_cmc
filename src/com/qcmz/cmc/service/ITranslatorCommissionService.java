package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.vo.TranslatorCommissionBean;

public interface ITranslatorCommissionService {
	/**
	 * 获取译员佣金统计列表
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 * @param operCd
	 * @return
	 */
	public List<TranslatorCommissionBean> findCommission(Date startDate, Date endDate, String operCd);
	/**
	 * 获取已处理的订单列表，带使用的用户套餐信息
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 * @param commissionStatus
	 * @param operCd
	 * @return
	 */
	public List<CmcROrder> findDealtOrder(Date startDate, Date endDate, Integer commissionStatus, String operCd);
	/**
	 * 获取译员佣金信息
	 * @param startDate
	 * @param endDate
	 * @param operCd
	 * @return
	 */
	public TranslatorCommissionBean getCommission(Date startDate, Date endDate, String operCd);
}
