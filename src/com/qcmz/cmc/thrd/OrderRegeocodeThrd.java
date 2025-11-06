package com.qcmz.cmc.thrd;

import com.qcmz.cmc.service.IOrderService;
import com.qcmz.framework.geography.GeographyBean;
import com.qcmz.framework.geography.GeographyUtil;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;

/**
 * 订单逆地理编码线程
 */
public class OrderRegeocodeThrd extends AbstractThrd {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	
	
	public OrderRegeocodeThrd(String orderId, String lon, String lat) {
		super();
		this.orderId = orderId;
		this.lon = lon;
		this.lat = lat;
	}

	@Override
	protected void work() {
		GeographyBean geography = GeographyUtil.regeocode(lon, lat, null);
		if(geography!=null){
			((IOrderService)SpringUtil.getBean("orderServiceImpl")).updateAddress(orderId, geography.getCountryName()+geography.getAddress());
		}
	}
	
	public static void start(String orderId, String lon, String lat){
		OrderRegeocodeThrd geoThrd = new OrderRegeocodeThrd(orderId, lon, lat);
		new Thread(geoThrd).start();
	}
}
