package com.qcmz.cmc.process;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.comparator.DialogMsgComparator;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.entity.CmcDialogMsg;
import com.qcmz.cmc.service.IDialogMsgService;
import com.qcmz.cmc.thrd.DialogNotifyThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.DialogMsgAddBean;
import com.qcmz.cmc.ws.provide.vo.DialogMsgAddResult;
import com.qcmz.cmc.ws.provide.vo.DialogMsgBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;

@Component
public class DialogProcess {
	@Autowired
	private IDialogMsgService dialogMsgService;
	
	/**
	 * 用户添加消息
	 * @param bean
	 * @exception ParamException
	 */
	public DialogMsgAddResult addMsg(DialogMsgAddBean bean){
		//base64解码
		String msg = SecretUtil.deBase64(bean.getMsg());
		if(msg.length()>4000){
			throw new ParamException("消息超长");
		}
		
		//保存消息
		CmcDialogMsg entity = dialogMsgService.saveUserMsg(bean.getUid(), msg);

		//返回
		DialogMsgAddResult result = new DialogMsgAddResult();
		result.setMsgId(entity.getMsgid());
		result.setMsgSide(entity.getMsgside());
		result.setMsg(entity.getMsg());
		result.setCreateTime(entity.getCreatetime().getTime());
		
		String mailSubject = "您有新的对话消息待处理";
		String mailMsg = StringUtil.abbreviate(msg, 100);
		MailUtil.sendSimpleMailThread(SystemConfig.CS_MAILS, mailSubject, mailMsg);
		
		return result;
	}
	
	/**
	 * 客服回复
	 * @param dialogId
	 * @param msg
	 * @param oper
	 */
	public void addCsMsg(Long dialogId, String msg, Operator oper){
		//入库
		Long userId = dialogMsgService.saveCsMsg(dialogId, msg, oper);
		
		//通知用户
		DialogNotifyThrd.notifyMsg(userId, dialogId);
	}
	
	/**
	 * 分页获取用户对话消息列表
	 * @param userId
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	public List<DialogMsgBean> findMsg(Long userId, int pageSize, String moreBaseId){
		Long lastMsgId = null;
		if(NumberUtil.isNumber(moreBaseId)){
			lastMsgId = Long.valueOf(moreBaseId);
		}
		List<CmcDialogMsg> list = dialogMsgService.findMsg(userId, pageSize, SystemConstants.SORT_DESC, lastMsgId);
		
		List<DialogMsgBean> result = BeanConvertUtil.toDialogMsgBean(list);
		
		//排序，便于前端展示
		Collections.sort(result, new DialogMsgComparator());
		
		return result;
	}
	
	/**
	 * 分页获取用户对话新消息列表
	 * @param userId
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	public List<DialogMsgBean> findNewMsg(Long userId, int pageSize, String moreBaseId){
		Long lastMsgId = null;
		if(NumberUtil.isNumber(moreBaseId)){
			lastMsgId = Long.valueOf(moreBaseId);
		}
		List<CmcDialogMsg> list = dialogMsgService.findMsg(userId, pageSize, SystemConstants.SORT_ASC, lastMsgId);
		
		return BeanConvertUtil.toDialogMsgBean(list);
	}
}
