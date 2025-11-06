package com.qcmz.cmc.comparator;

import java.util.Comparator;

import com.qcmz.cmc.vo.TransferProxyBean;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class TransferProxyComparator implements Comparator<TransferProxyBean> {

	/** 
	 * 通道越少越靠前
	 * @param o1
	 * @param o2
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	@Override
	public int compare(TransferProxyBean o1, TransferProxyBean o2) {
		return o1.getProxyIdCount().compareTo(o2.getProxyIdCount());
	}

}
