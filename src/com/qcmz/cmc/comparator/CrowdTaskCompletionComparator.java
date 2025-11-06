package com.qcmz.cmc.comparator;

import java.util.Comparator;

import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionListBean;

/**
 * 类说明：完成时间倒序
 * 修改历史：
 */
public class CrowdTaskCompletionComparator implements Comparator<CrowdTaskCompletionListBean> {
	/** 
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(CrowdTaskCompletionListBean o1, CrowdTaskCompletionListBean o2) {
		if(o1.getFinishTime()>o2.getFinishTime()){
			return -1;
		}else if(o1.getFinishTime()<o2.getFinishTime()){
			return 1;
		}else{
			return 0;
		}
	}

}
