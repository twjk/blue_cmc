package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcRLog;
import com.qcmz.cmc.ws.provide.vo.OrderLogBean;
import com.qcmz.framework.dao.IBaseDAO;

public interface IOrderLogDao extends IBaseDAO {
	/**
	 * 获取操作历史
	 * @param orderId
	 * @param logType
	 * @return
	 * 修改历史：
	 */
	public List<CmcRLog> findLog(String orderId, String logType);
	/**
	 * 获取操作历史
	 * @param orderId
	 * @param logType
	 * @param withOperName 是否带操作人姓名
	 * @return
	 */
	public List<OrderLogBean> findLogInfo(String orderId, String logType, boolean withOperName);
	/**
	 * 清除日志
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public void clearLog(String orderId);
}
