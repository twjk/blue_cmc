package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcDialogMsg;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IDialogMsgService {
	/**
	 * 分页获取对话消息
	 * @param map
	 * @param pageBean<CmcDialogMsg>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取对话消息列表
	 * @param dialogId
	 * @return
	 */
	public List<CmcDialogMsg> findMsg(Long dialogId);
	/**
	 * 分页获取对话消息列表
	 * @param userId
	 * @param pageSize
	 * @param sort
	 * @param lastMsgId
	 * @return
	 */
	public List<CmcDialogMsg> findMsg(Long userId, int pageSize, String sort, Long lastMsgId);
	/**
	 * 添加用户消息
	 * @param userId
	 * @param msg
	 */
	public CmcDialogMsg saveUserMsg(Long userId, String msg);
	/**
	 * 添加客服消息
	 * @param dialogId
	 * @param msg
	 * @param oper
	 * @return
	 */
	public Long saveCsMsg(Long dialogId, String msg, Operator oper);
}
