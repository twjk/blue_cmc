package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.ws.provide.vo.OrderMsgBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IOrderMsgService {
	/**
	 * 以订单为单位分页最新留言列表，带订单、用户信息
	 * @param map
	 * @param pageBean<CmcRMsg>
	 * @return 
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取留言列表，倒序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<OrderMsgBean> findMsg(String orderId, int pageSize, Long lastId);
	/**
	 * 以订单为单位分页获取用户最新留言列表
	 * @param userId
	 * @param pageBean
	 * @return 
	 */
	public List<OrderMsgBean> findRecentMsg4User(Long userId, PageBean pageBean);
	/**
	 * 分页获取指定订单的留言列表
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<OrderMsgBean> findMsg4User(String orderId, Long userId, int pageSize, Long lastId);
	/**
	 * 分页获取指定订单的新留言列表
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<OrderMsgBean> findLastMsg4User(String orderId, Long userId, int pageSize, Long lastId);
	/**
	 * 添加用户留言
	 * @param userId
	 * @param orderId
	 * @param msg
	 * 修改历史：
	 */
	public void saveMsgOfUser(String orderId, Long userId, String msg);
	/**
	 * 添加客服留言
	 * @param orderId
	 * @param msg
	 * @param oper
	 * 修改历史：
	 */
	public void saveMsgOfCs(String orderId, String msg, Operator oper);
}
