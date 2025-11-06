package com.qcmz.cmc.thrd;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.process.OrderCommissionProcess;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;

/**
 * 订单佣金结算
 */
public class OrderCommissionThrd extends AbstractThrd {
	private static OrderCommissionProcess orderCommissionProcess;
	private CmcROrder order;
	
	public OrderCommissionThrd(CmcROrder order) {
		super();
		this.order = order;
	}

	@Override
	protected void work() {
		if(orderCommissionProcess==null){
			orderCommissionProcess = (OrderCommissionProcess) SpringUtil.getBean("orderCommissionProcess");
		}
		orderCommissionProcess.commission(order);
	}
	
	public static void start(CmcROrder order){
		new Thread(new OrderCommissionThrd(order)).start();
	}
}
