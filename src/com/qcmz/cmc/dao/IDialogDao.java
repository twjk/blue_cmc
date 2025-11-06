package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IDialogDao extends IBaseDAO {
	/**
	 * 分页获取对话
	 * @param map
	 * @param pageBean<CmcDialog>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户对话
	 * @param userId
	 * @return
	 */
	public CmcDialog getDialog(Long userId);
	/**
	 * 更新处理状态
	 * @param dialogId
	 * @param dealStatus
	 */
	public void updateDealStatus(Long dialogId, Integer dealStatus);
}
