package com.qcmz.cmc.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IDialogDao;
import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.cmc.service.IDialogMsgService;
import com.qcmz.cmc.service.IDialogService;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class DialogServiceImpl implements IDialogService {
	@Autowired
	private IDialogDao dialogDao;
	@Autowired
	private IDialogMsgService dialogMsgService;
	
	/**
	 * 分页获取对话，带用户信息
	 * @param map
	 * @param pageBean<CmcDialog>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		dialogDao.queryByMapTerm(map, pageBean);
		
		List<CmcDialog> list = (List<CmcDialog>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcDialog dialog : list) {
			userIds.add(dialog.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcDialog dialog : list) {
			dialog.setUser(userMap.get(dialog.getUserid()));
		}
	}
	
	/**
	 * 获取对话信息
	 * @param dialogId
	 * @return
	 */
	public CmcDialog getDialog(Long dialogId){
		return (CmcDialog) dialogDao.load(CmcDialog.class, dialogId);
	}
	
	/**
	 * 获取对话信息
	 * @param dialogId，带用户/消息
	 * @return
	 */
	public CmcDialog getDialogJoin(Long dialogId){
		CmcDialog result = getDialog(dialogId);
		
		//用户
		result.setUser(UserMap.getUser(result.getUserid()));
		//消息列表
		result.setMsgs(dialogMsgService.findMsg(result.getDialogid()));
		
		return result;
	}
	
	/**
	 * 获取用户对话
	 * @param userId
	 * @return
	 */
	public CmcDialog getDialogByUserId(Long userId){
		return dialogDao.getDialog(userId);
	}
	
	/**
	 * 添加用户对话
	 * @param userId
	 * @return
	 */
	public CmcDialog saveDialog(Long userId){
		CmcDialog dialog = new CmcDialog();
		dialog.setUserid(userId);
		dialogDao.save(dialog);
		return dialog;
	}
	
	/**
	 * 更新处理状态
	 * @param dialogId
	 * @param dealStatus
	 */
	public void updateDealStatus(Long dialogId, Integer dealStatus){
		dialogDao.updateDealStatus(dialogId, dealStatus);
	}
}
