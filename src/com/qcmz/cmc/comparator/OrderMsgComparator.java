package com.qcmz.cmc.comparator;

import java.util.Comparator;

import com.qcmz.cmc.ws.provide.vo.OrderMsgBean;

/**
 * 类说明：msgId正序
 * 修改历史：
 */
public class OrderMsgComparator implements Comparator<OrderMsgBean> {

	/** 
	 * @param o1
	 * @param o2
	 * @return
	 * 修改历史：
	 */
	@Override
	public int compare(OrderMsgBean o1, OrderMsgBean o2) {
		return (int)(o1.getMsgId()-o2.getMsgId());
	}

}
