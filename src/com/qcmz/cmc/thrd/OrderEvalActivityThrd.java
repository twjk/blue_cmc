package com.qcmz.cmc.thrd;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.ITaskService;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.ActivityWS;

/**
 * 订单评价参加活动
 */
public class OrderEvalActivityThrd extends AbstractThrd {
	private ITaskService taskService;
	private CmcROrder order;
	
	public OrderEvalActivityThrd(CmcROrder order) {
		super();
		this.order = order;
	}

	@Override
	protected void work() {
		Response resp = ActivityWS.evalOrder(order.getUserid(), order.getOrdertype(), order.getOrderid(), order.getPayamount());
		if(!resp.successed()){
			if(taskService==null){
				taskService = (ITaskService) SpringUtil.getBean("taskServiceImpl");
			}
			taskService.saveTask(DictConstant.DICT_TASKTYPE_EVALORDER, order.getOrderid(), null);
		}
	}

	public static void start(CmcROrder order){
		if(!DictConstant.DICT_ORDERCAT_TRANS.equals(order.getOrdercat())) return;
		OrderEvalActivityThrd partThrd = new OrderEvalActivityThrd(order);
		new Thread(partThrd).start();
	}
}
