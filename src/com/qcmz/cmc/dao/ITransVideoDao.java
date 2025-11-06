package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcVtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealListBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ITransVideoDao extends IBaseDAO {
	/**
	 * 分页获取视频口译信息，含订单信息
	 * @param map
	 * @param pageBean<CmcVtTrans>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取视频口译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransVideoDealListBean> findVideo4Deal(OrderDealQueryBean query, int pageSize);
	/**
	 * 获取视频口译列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcVtTrans> findVideo(List<String> orderIds);
	/**
	 * 获取视频口译信息
	 * @param orderId
	 * @return
	 */
	public CmcVtTrans getTrans(String orderId);
	/**
	 * 更新接通时间
	 * @param orderId
	 * 修改历史：
	 */
	public void updateConnectTime(String orderId);
	/**
	 * 更新开始计费时间
	 * @param orderId
	 * 修改历史：
	 */
	public void updateStartBillingTime(String orderId);
	/**
	 * 更新接通和开始计费时间
	 * @param orderId
	 * 修改历史：
	 */
	public void updateConnectAndStartBillingTime(String orderId);
}
