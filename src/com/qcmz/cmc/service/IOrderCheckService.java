package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IOrderCheckService {
	/**
	 * 分页获取校对订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findCheck(Map<String, String> map, PageBean pageBean);
	/**
	 * 开始修订
	 * @param orderId
	 * @param oper
	 */
	public void startCheck(String orderId, Operator oper);
	/**
	 * 保存图片翻译校对结果
	 * @param order
	 * @param bean
	 */
	public void saveCheck4Pic(TransPicDealBean bean, String dealStatus);
	/**
	 * 保存完成图片翻译校对结果
	 * @param orderId
	 * @param dealStatus
	 * @param revise
	 * @param checkStatus
	 * @param dealBean null表示没有修改翻译结果
	 */
	public void saveFinishCheck4Pic(String orderId, String dealStatus, Integer revise, Integer checkStatus, TransPicDealBean dealBean);
	/**
	 * 保存校对修改的文本翻译结果
	 * @param dealBean
	 * @param dealStatus
	 */
	public void saveCheck4Text(TransTextDealBean dealBean, String dealStatus);
	/**
	 * 保存完成文本翻译校对结果
	 * @param orderId
	 * @param dealStatus
	 * @param revise
	 * @param checkStatus
	 * @param dealBean
	 */
	public void saveFinishCheck4Text(String orderId, String dealStatus, Integer revise, Integer checkStatus, TransTextDealBean dealBean);
}
