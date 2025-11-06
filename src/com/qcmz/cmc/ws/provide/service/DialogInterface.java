package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.DialogProcess;
import com.qcmz.cmc.ws.provide.vo.DialogMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.DialogMsgAddResp;
import com.qcmz.cmc.ws.provide.vo.DialogMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.DialogMsgQueryResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 对话相关接口
 */
@Component
public class DialogInterface extends BaseInterface {
	@Autowired
	private DialogProcess dialogProcess;
	
	/**
	 * 添加消息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public DialogMsgAddResp addMsg(DialogMsgAddReq req, String interfaceType, String reqIp){
		DialogMsgAddResp resp = new DialogMsgAddResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getMsg())){
					resp.errorParam("消息为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(dialogProcess.addMsg(req.getBean()));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
	
		return resp;
	}
	
	/**
	 * 分页获取用户消息列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public DialogMsgQueryResp findMsg(DialogMsgQueryReq req, String pageSize, String interfaceType, String reqIp){
		DialogMsgQueryResp resp = new DialogMsgQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(dialogProcess.findMsg(req.getUid(), pageBean.getPageSize(), req.getMoreBaseId()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取用户新的消息列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public DialogMsgQueryResp findNewMsg(DialogMsgQueryReq req, String pageSize, String interfaceType, String reqIp){
		DialogMsgQueryResp resp = new DialogMsgQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(dialogProcess.findNewMsg(req.getUid(), pageBean.getPageSize(), req.getMoreBaseId()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
}
