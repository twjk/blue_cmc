package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.cmc.entity.CmcVtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoAddBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDetailBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;

public interface ITransVideoService {
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
	 * 获取视频口译详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public TransVideoDetailBean getVideoDetail(String orderId, Long userId);
	/**
	 * 获取视频口译订单信息，含翻译、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取视频口译信息
	 * @param orderId
	 * @return
	 */
	public CmcVtTrans getTrans(String orderId);
	/**
	 * 创建视频口译订单
	 * @param orderId
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addTransVideo(String orderId, TransVideoAddBean bean, String serviceType, String platform, String version);
	/**
	 * 接通用户
	 * @param dealBean
	 * 修改历史：
	 */
	public void connected(TransVideoDealBean dealBean);
	/**
	 * 开始计费
	 * @param dealBean
	 * 修改历史：
	 */
	public void startBilling(TransVideoDealBean dealBean);
	/**
	 * 接通并开始计费
	 * @param dealBean
	 */
	public void connectedAndBilling(TransVideoDealBean dealBean);
	/**
	 * 完成翻译
	 * @param order
	 * @param trans
	 * @param dealBean
	 * @param price
	 */
	public void finishTrans(CmcROrder order, CmcVtTrans trans, TransVideoDealBean dealBean, CmcTpPrice price);
	/**
	 * 拒单处理
	 * @param orderId
	 * @param date
	 */
	public void rejectOrder(String orderId, Date date);
}
