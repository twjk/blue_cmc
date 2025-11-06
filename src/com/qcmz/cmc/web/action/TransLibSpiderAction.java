package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.SpiderBookingProcess;
import com.qcmz.framework.action.BaseAction;

/**
 * 类说明：数据维护
 * 修改历史：
 */
public class TransLibSpiderAction extends BaseAction {
	@Autowired
	private SpiderBookingProcess spiderBookingProcess;
	
	//从booking采集酒店名称
	public String doSpiderBooking(){
		try {
			int count = spiderBookingProcess.spider(getParameterMap());
			setResult("成功采集记录数："+count);
		} catch (Exception e) {
			logger.error("从booking采集酒店名称失败",e);
			handleResult = false;
			setResult("从booking采集酒店名称失败");
		}
		
		print();
		
		return null;
	}
	
}
