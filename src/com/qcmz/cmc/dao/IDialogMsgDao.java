package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcDialogMsg;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IDialogMsgDao extends IBaseDAO {
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
}
