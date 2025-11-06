package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.cmc.entity.CmcRPackage;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IPackageOrderDao extends IBaseDAO {
	/**
	 * 分页获取优惠券包，含订单信息
	 * @param map
	 * @param pageBean<CmcRPackage>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取优惠券包信息
	 * @param orderId
	 * @return
	 */
	public CmcRPackage getPackage(String orderId);
}
