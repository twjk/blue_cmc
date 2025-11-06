package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.framework.page.PageBean;

public interface IDialogService {
	/**
	 * 分页获取对话，带用户信息
	 * @param map
	 * @param pageBean<CmcDialog>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取对话信息
	 * @param dialogId
	 * @return
	 */
	public CmcDialog getDialog(Long dialogId);
	/**
	 * 获取对话信息
	 * @param dialogId，带消息
	 * @return
	 */
	public CmcDialog getDialogJoin(Long dialogId);
	/**
	 * 获取用户对话
	 * @param userId
	 * @return
	 */
	public CmcDialog getDialogByUserId(Long userId);
	/**
	 * 添加用户对话
	 * @param userId
	 * @return
	 */
	public CmcDialog saveDialog(Long userId);
	/**
	 * 更新处理状态
	 * @param dialogId
	 * @param dealStatus
	 */
	public void updateDealStatus(Long dialogId, Integer dealStatus);
}
