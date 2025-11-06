package com.qcmz.cmc.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.CalendarWS;
import com.qcmz.bdc.ws.provide.vo.HolidayBean;
import com.qcmz.bdc.ws.provide.vo.HolidayPageBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.DateUtil;

@Component
public class HolidayMap extends AbstractCacheMap {
	private Map<String, HolidayBean> map = null;
	public static boolean init = false;
	
	@Override
	protected void init() {
		int page = 0;
		String pageSize = "1000";
		long pageCount = 0;
		String beginDate = "2018-01-01";
		String endDate = null;
		
		Map<String, HolidayBean> mapTemp = new HashMap<String, HolidayBean>();
		HolidayPageBean pageBean = null;
		do{
			page++;
			pageBean = CalendarWS.findHoliday(beginDate, endDate, "cn", String.valueOf(page), pageSize);
			if(pageBean==null){
				logger.error("获取节假日失败");
				mapTemp.clear();
				return;
			}
			
			for (HolidayBean bean : pageBean.getResultList()) {
				mapTemp.put(bean.getDate(), bean);
			}
			pageCount = pageBean.getPageCount();
		}while(page<pageCount);
		
		map = mapTemp;
	}

	public boolean hasInit(){
		return safeInit(map);
	}
	
	public HolidayBean getBean(Date date){
		if(safeInit(map)){
			return map.get(DateUtil.formatDate(date));
		}
		return null;
	}
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}
}
