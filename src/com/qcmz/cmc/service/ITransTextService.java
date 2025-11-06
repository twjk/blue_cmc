package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.cmc.ws.provide.vo.TransTextAddBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDetailBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;

public interface ITransTextService {
	/**
	 * 分页获取短文快译信息，含订单信息
	 * @param map
	 * @param pageBean<CmcTtTrans>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取短文快译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransTextDealListBean> findText4Deal(OrderDealQueryBean query, int pageSize);
	/**
	 * 获取短文快译列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcTtTrans> findText(List<String> orderIds);
	/**
	 * 获取短文快译订单信息，含翻译、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取短文快译信息
	 * @param orderId
	 * @return
	 */
	public CmcTtTrans getTrans(String orderId);
	/**
	 * 获取短文快译详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public TransTextDetailBean getTextDetail(String orderId, Long userId);
	/**
	 * 获取短文快译详细信息
	 * @param orderId
	 * @return
	 * @exception ParamException
	 */
	public TransTextDealDetailBean getTextDetail4Deal(String orderId);
	/**
	 * 创建短文快译订单
	 * @param orderId
	 * @param userId
	 * @param pushRegid
	 * @param transModel
	 * @param sessionId
	 * @param from
	 * @param to
	 * @param src
	 * @param dst
	 * @param similar
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addTransText(String orderId, TransTextAddBean bean, TransPriceBean price, int similar, String serviceType, String platform, String version);
	/**
	 * 保存翻译结果
	 * @param dealBean
	 */
	public void saveTrans(TransTextDealBean dealBean);
	/**
	 * 保存翻译结果，无校验
	 * @param dealBean
	 * @param dealStatus
	 */
	public void saveTransResult(TransTextDealBean dealBean, String dealStatus);
	/**
	 * 完成短文快译
	 * @param dealBean
	 * @param dealStatus
	 */
	public void finishTrans(TransTextDealBean dealBean, String dealStatus);
	/**
	 * 更新语音
	 * @param orderId
	 * @param voice
	 */
	public void updateVoice(String orderId, String voice);
}
