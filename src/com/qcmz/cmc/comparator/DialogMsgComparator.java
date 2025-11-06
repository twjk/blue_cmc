package com.qcmz.cmc.comparator;

import java.util.Comparator;

import com.qcmz.cmc.ws.provide.vo.DialogMsgBean;

/**
 * 类说明：msgId正序
 * 修改历史：
 */
public class DialogMsgComparator implements Comparator<DialogMsgBean> {

	/** 
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(DialogMsgBean o1, DialogMsgBean o2) {
		return o1.getMsgId().compareTo(o2.getMsgId());
	}

}
