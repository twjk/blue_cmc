package com.qcmz.cmc.process;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcSTask;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITaskService;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.ActivityWS;

/**
 * 类说明：任务处理
 * 修改历史：
 */
@Component
public class TaskProcess {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ITaskService taskService;
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 处理完成订单活动任务
	 * 修改历史：
	 */
	public void finishOrderActivity(){
		List<CmcSTask> list = taskService.findTask4Process(DictConstant.DICT_TASKTYPE_FINISHORDER, 20);
		if(list.isEmpty()){
			return;
		}
		logger.info("待处理完成订单活动数："+list.size());
		
		CmcROrder order = null;
		boolean success;
		String operResult = null;
		for (CmcSTask task : list) {
			success = true;
			operResult = null;
			try {
				order = orderService.getOrder(task.getIdentify());
				if(DictConstant.DICT_ORDER_DEALSTATUS_SUB.equals(order.getDealstatus())
						|| DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(order.getDealstatus())){
					Response resp = ActivityWS.finishOrder(order.getUserid(), order.getOrdertype(), order.getOrderid(), order.getPayamount(), order.getInvitecode());
					success = resp.successed();
					operResult = resp.toString();
				}else{
					operResult = "无需处理";
				}
			} catch (Exception e) {
				success = false;
				operResult = e.getMessage();
			}
			taskService.saveOperResult(task, success, operResult);
		}
		
		logger.info("完成处理完成订单活动");
		
	}
	
	/**
	 * 处理评价订单活动任务
	 * 修改历史：
	 */
	public void evalOrderActivity(){
		List<CmcSTask> list = taskService.findTask4Process(DictConstant.DICT_TASKTYPE_EVALORDER, 20);
		if(list.isEmpty()){
			return;
		}
		logger.info("待处理评价订单活动数："+list.size());
		
		CmcROrder order = null;
		boolean success;
		String operResult = null;
		for (CmcSTask task : list) {
			success = true;
			operResult = null;
			try {
				order = orderService.getOrder(task.getIdentify());
				if(DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(order.getDealstatus())){
					Response resp = ActivityWS.evalOrder(order.getUserid(), order.getOrdertype(), order.getOrderid(), order.getPayamount());
					success = resp.successed();
					operResult = resp.toString();					
				}else{
					operResult = "无需处理";
				}
			} catch (Exception e) {
				success = false;
				operResult = e.getMessage();
			}
			taskService.saveOperResult(task, success, operResult);
		}
		
		logger.info("完成处理评价订单活动");
		
	}
	
	/**
	 * 监控任务异常
	 * 修改历史：
	 */
	public void monitorAbnormal(){
		StringBuilder sb = new StringBuilder();
		
		Long count = taskService.getAbnormalCount();
		if(count>0){
			sb.append("UMC系统").append(count).append("个任务超出重试次数");
			MailUtil.sendSimpleMail(SystemConfig.MAILS, null, sb.toString(), sb.toString());
		}
	}
}
