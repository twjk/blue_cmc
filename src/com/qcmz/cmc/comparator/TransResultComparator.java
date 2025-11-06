package com.qcmz.cmc.comparator;

import java.util.Comparator;

import com.qcmz.cmc.ws.provide.vo.TransDstBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransResultComparator implements Comparator<TransDstBean>{
	/** 
	 * 相似度倒序
	 * @param o1
	 * @param o2
	 * @return
	 * 修改历史：
	 */
	@Override
	public int compare(TransDstBean o1, TransDstBean o2) {
		return Integer.valueOf(o2.getSimilar()).compareTo(o1.getSimilar());
	}

}
