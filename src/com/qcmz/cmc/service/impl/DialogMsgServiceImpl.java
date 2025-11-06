package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IDialogMsgDao;
import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.cmc.entity.CmcDialogMsg;
import com.qcmz.cmc.service.IDialogMsgService;
import com.qcmz.cmc.service.IDialogService;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class DialogMsgServiceImpl implements IDialogMsgService {
	@Autowired
	private IDialogMsgDao dialogMsgDao;
	@Autowired
	private IDialogService dialogService;
	
	/**
	 * 分页获取对话消息
	 * @param map
	 * @param pageBean<CmcDialogMsg>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		dialogMsgDao.queryByMapTerm(map, pageBean);
		
		List<CmcDialogMsg> list = (List<CmcDialogMsg>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcDialogMsg msg : list) {
			userIds.add(msg.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcDialogMsg msg : list) {
			msg.setUser(userMap.get(msg.getUserid()));
		}
	}
	
	/**
	 * 获取对话消息列表
	 * @param dialogId
	 * @return
	 */
	public List<CmcDialogMsg> findMsg(Long dialogId){
		return dialogMsgDao.findMsg(dialogId);
	}
	
	/**
	 * 分页获取对话消息列表
	 * @param userId
	 * @param pageSize
	 * @param sort
	 * @param lastMsgId
	 * @return
	 */
	public List<CmcDialogMsg> findMsg(Long userId, int pageSize, String sort, Long lastMsgId){
		return dialogMsgDao.findMsg(userId, pageSize, sort, lastMsgId);
	}
	
	/**
	 * 添加用户消息
	 * @param userId
	 * @param msg
	 */
	public CmcDialogMsg saveUserMsg(Long userId, String msg){
		CmcDialog dialog = dialogService.getDialogByUserId(userId);
		if(dialog==null){
			dialog = dialogService.saveDialog(userId);
		}
		
		Date now = new Date();

		CmcDialogMsg entity = new CmcDialogMsg();
		entity.setUserid(userId);
		entity.setDialogid(dialog.getDialogid());
		entity.setMsgside(DictConstant.DICT_DIALOG_MSGSIDE_USER);
		entity.setMsg(msg);
		entity.setCreatetime(now);
		
		dialogMsgDao.save(entity);
		
		dialog.setMsgtime(now);
		dialog.setDealstatus(DictConstant.DICT_DIALOG_DEALSTATUS_WAIT);
		dialogMsgDao.update(dialog);
		
		return entity;
	}
	
	/**
	 * 添加客服消息
	 * @param dialogId
	 * @param msg
	 * @param oper
	 * @return
	 */
	public Long saveCsMsg(Long dialogId, String msg, Operator oper){
		CmcDialog dialog = dialogService.getDialog(dialogId);
		CmcDialogMsg dialogMsg = null;
		
		if(StringUtil.notBlankAndNull(msg)){
			dialogMsg = new CmcDialogMsg();
			dialogMsg.setUserid(dialog.getUserid());
			dialogMsg.setDialogid(dialog.getDialogid());
			dialogMsg.setMsgside(DictConstant.DICT_DIALOG_MSGSIDE_AGENT);
			dialogMsg.setMsg(msg);
			dialogMsg.setCreatetime(new Date());
			dialogMsg.setMsgcd(oper.getOperCd());
			dialogMsg.setMsgname(oper.getOperName());
			dialogMsgDao.save(dialogMsg);
		}
		
		if(DictConstant.DICT_DIALOG_DEALSTATUS_WAIT.equals(dialog.getDealstatus())){
			dialogService.updateDealStatus(dialogId, DictConstant.DICT_DIALOG_DEALSTATUS_DEALT);
		}
		
		return dialog.getUserid();
	}
}
