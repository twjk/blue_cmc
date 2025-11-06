package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcRMsg;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IOrderMsgDao extends IBaseDAO {
	/**
	 * 以订单为单位分页获取最新留言列表，带订单信息
	 * @param sideType
	 * @param userId
	 * @param orderId 结束以
	 * @param pageBean<CmcRMsg>
	 * @return 
	 */
	public void findRecentMsg(String sideType, Long userId, String orderId, PageBean pageBean);
	/**
	 * 获取指定订单的留言列表
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public List<CmcRMsg> findMsg(String orderId);
	/**
	 * 分页获取指定订单的留言列表，倒序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<CmcRMsg> findMsg(String orderId, Long userId, int pageSize, Long lastId);
	/**
	 * 分页获取指定订单的留言列表，正序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<CmcRMsg> findLastMsg(String orderId, Long userId, int pageSize, Long lastId);
	/**
	 * 清除留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public void clearMsg(String orderId);
}
