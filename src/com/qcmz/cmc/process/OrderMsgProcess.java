package com.qcmz.cmc.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IOrderMsgService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.thrd.OrderMsgNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.ws.provide.vo.OrderMsgAddBean;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.log.Operator;

@Component
public class OrderMsgProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderMsgService orderMsgService;
	
	/**
	 * 添加用户留言
	 * @param orderId
	 * @param userId
	 * @param msg
	 * @exception ParamException
	 * 修改历史：
	 */
	public void addMsgOfUser(OrderMsgAddBean bean){
		CmcROrder order = orderService.getOrder(bean.getOrderId(), bean.getUid());
		if(order==null){
			throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		
		orderMsgService.saveMsgOfUser(bean.getOrderId(), bean.getUid(), bean.getMsg());
		
		OrderMsgNotifyCsThrd.msgNotify(bean.getOrderId(), order.getOrdertype(), bean.getMsg(), order.getOpercd());
	}
	
	/**
	 * 添加客服留言
	 * @param orderId
	 * @param userId
	 * @param msg
	 * 修改历史：
	 */
	public void addCsMsg(String orderId, String msg, Operator oper){
		orderMsgService.saveMsgOfCs(orderId, msg, oper);
		
		//给用户发通知
		CmcROrder order = orderService.getOrder(orderId);
		OrderNotifyThrd.notifyMsg(order.getServicetype(), order.getUserid(), orderId, msg);
	}
}
