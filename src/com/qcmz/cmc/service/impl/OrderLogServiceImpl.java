package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderLogDao;
import com.qcmz.cmc.entity.CmcRLog;
import com.qcmz.cmc.service.IOrderLogService;

@Service
public class OrderLogServiceImpl implements IOrderLogService {
	@Autowired
	private IOrderLogDao orderLogDao;
	
	/**
	 * 保存流转日志
	 * @param orderId
	 * @param dealStatus
	 * @param operCd
	 * @param operName
	 * @param operTime
	 * 修改历史：
	 */
	public void saveFlowLog(String orderId, String dealStatus, String operCd, String operName, Date operTime){
		saveFlowLog(orderId, dealStatus, operCd, operName, operTime, null);
	}
	
	/**
	 * 保存流转日志
	 * @param orderId
	 * @param dealStatus
	 * @param operCd
	 * @param operName
	 * @param operTime
	 * @param log
	 * 修改历史：
	 */
	public void saveFlowLog(String orderId, String dealStatus, String operCd, String operName, Date operTime, String log){
		CmcRLog cmcRLog = new CmcRLog();
		cmcRLog.setOrderid(orderId);
		cmcRLog.setDealstatus(dealStatus);
		cmcRLog.setLogtype(DictConstant.DICT_LOGTYPE_FLOW);
		cmcRLog.setLog(log);
		cmcRLog.setOpercd(operCd);
		cmcRLog.setOpername(operName);
		cmcRLog.setOpertime(operTime);
		orderLogDao.save(cmcRLog);
	}
	
	/**
	 * 保存操作日志
	 * @param orderId
	 * @param dealStatus
	 * @param operCd
	 * @param operName
	 * @param log
	 * 修改历史：
	 */
	public void saveOperLog(String orderId, String dealStatus, String operCd, String operName, String log){
		CmcRLog cmcRLog = new CmcRLog();
		cmcRLog.setOrderid(orderId);
		cmcRLog.setDealstatus(dealStatus);
		cmcRLog.setLogtype(DictConstant.DICT_LOGTYPE_OPER);
		cmcRLog.setLog(log);
		cmcRLog.setOpercd(operCd);
		cmcRLog.setOpername(operName);
		
		cmcRLog.setOpertime(new Date());
		orderLogDao.save(cmcRLog);
	}
}
