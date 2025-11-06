package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IOrderDao;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IOrderCheckService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

@Service
public class OrderCheckServiceImpl implements IOrderCheckService {
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransTextService transTextService;
	
	/**
	 * 分页获取校对订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findCheck(Map<String, String> map, PageBean pageBean){
		orderDao.findCheck(map, pageBean);
	}
	
	/**
	 * 开始校对
	 * @param orderId
	 * @param oper
	 * @exception DataException
	 */
	public void startCheck(String orderId, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!OrderUtil.canStartCheck(order.getCheckstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		order.setCheckcd(oper.getOperCd());
		order.setCheckname(oper.getOperName());
		order.setCheckstatus(DictConstants.DICT_CHECKSTATUS_CHECKING);
		order.setRevise(SystemConstants.STATUS_OFF);
		order.setCheckstarttime(new Date());

		orderDao.update(order);
	}
	
	/**
	 * 保存图片翻译校对结果
	 * @param order
	 * @param bean
	 */
	public void saveCheck4Pic(TransPicDealBean bean, String dealStatus){
		transPicService.saveTransResult(bean, dealStatus);
		orderService.saveOrderDeal(bean.getOrderId(), null, bean.getFrom(), bean.getTo(), bean.getOperator(), bean.getOperatorName());
		orderDao.updateRevise(bean.getOrderId(), SystemConstants.STATUS_ON);
	}
	
	/**
	 * 保存完成图片翻译校对结果
	 * @param orderId
	 * @param dealStatus
	 * @param revise
	 * @param checkStatus
	 * @param dealBean null表示没有修改翻译结果
	 */
	public void saveFinishCheck4Pic(String orderId, String dealStatus, Integer revise, Integer checkStatus, TransPicDealBean dealBean){
		if(dealBean!=null){
			transPicService.saveTransResult(dealBean, dealStatus);
			orderService.saveOrderDeal(orderId, null, dealBean.getFrom(), dealBean.getTo(), dealBean.getOperator(), dealBean.getOperatorName());
		}
		orderDao.saveFinishCheck(orderId, revise, checkStatus);
	}
	
	/**
	 * 保存校对修改的文本翻译结果
	 * @param dealBean
	 * @param dealStatus
	 */
	public void saveCheck4Text(TransTextDealBean dealBean, String dealStatus){
		orderService.saveOrderDeal(dealBean.getOrderId(), null, dealBean.getFrom(), dealBean.getTo(), dealBean.getOperator(), dealBean.getOperatorName());
		transTextService.saveTransResult(dealBean, dealStatus);
		orderDao.updateRevise(dealBean.getOrderId(), SystemConstants.STATUS_ON);
	}
	
	/**
	 * 保存完成文本翻译校对结果
	 * @param orderId
	 * @param dealStatus
	 * @param revise
	 * @param checkStatus
	 * @param dealBean null表示没有修改翻译结果
	 */
	public void saveFinishCheck4Text(String orderId, String dealStatus, Integer revise, Integer checkStatus, TransTextDealBean dealBean){
		if(dealBean!=null){
			orderService.saveOrderDeal(dealBean.getOrderId(), null, dealBean.getFrom(), dealBean.getTo(), dealBean.getOperator(), dealBean.getOperatorName());
			transTextService.saveTransResult(dealBean, dealStatus);
		}
		orderDao.saveFinishCheck(orderId, revise, checkStatus);
	}
}
