package com.qcmz.cmc.thrd;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.ITaskService;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.ActivityWS;

public class FinishOrderActivityThrd extends AbstractThrd {
	private ITaskService taskService;
	private CmcROrder order;
	
	public FinishOrderActivityThrd(CmcROrder order) {
		super();
		this.order = order;
	}

	@Override
	protected void work() {
		Response resp = ActivityWS.finishOrder(order.getUserid(), order.getOrdertype(), order.getOrderid(), order.getPayamount(), order.getInvitecode());
		if(!resp.successed()){
			if(taskService==null){
				taskService = (ITaskService) SpringUtil.getBean("taskServiceImpl");
			}
			taskService.saveTask(DictConstant.DICT_TASKTYPE_FINISHORDER, order.getOrderid(), null);
		}
	}

	public static void start(CmcROrder order){
		//20181029改由评价订单后送券，完成订单活动暂停
//		FinishOrderActivityThrd partThrd = new FinishOrderActivityThrd(order);
//		new Thread(partThrd).start();
	}
	
}
