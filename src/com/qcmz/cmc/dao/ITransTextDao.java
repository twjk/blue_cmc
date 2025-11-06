package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransTextTransBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ITransTextDao extends IBaseDAO {
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
	 * 获取用户之前翻译记录
	 * @param userId
	 * @param pageSize
	 * @param baseOrderId
	 * @return
	 */
	public List<TransTextTransBean> findUserTransBefore(Long userId, int pageSize, String baseOrderId);
	/**
	 * 获取用户之后翻译记录
	 * @param userId
	 * @param pageSize
	 * @param baseOrderId
	 * @return
	 */
	public List<TransTextTransBean> findUserTransAfter(Long userId, int pageSize, String baseOrderId);
	/**
	 * 获取短文快译列表
	 * @param picIds
	 * @return
	 */
	public List<CmcTtTrans> findText(List<String> orderIds);
	/**
	 * 获取短文快译信息
	 * @param orderId
	 * @return
	 */
	public CmcTtTrans getTrans(String orderId);
	/**
	 * 更新语音
	 * @param orderId
	 * @param voice
	 */
	public void updateVoice(String orderId, String voice);
}
