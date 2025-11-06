package com.qcmz.cmc.ws.provide.webservice.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.bdc.ws.provide.locator.LogInterfaceWS;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.process.TransProcess;
import com.qcmz.cmc.service.ITransFavService;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.cmc.service.ITransService;
import com.qcmz.cmc.ws.provide.vo.TransFavAddReq;
import com.qcmz.cmc.ws.provide.vo.TransFavQryReq;
import com.qcmz.cmc.ws.provide.vo.TransLogReq;
import com.qcmz.cmc.ws.provide.vo.TransPermitReq;
import com.qcmz.cmc.ws.provide.vo.TransPermitResp;
import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransResp;
import com.qcmz.cmc.ws.provide.webservice.ITransWS;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.RespConstants;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.AuthUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
@Service
public class TransWSImpl implements ITransWS {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ITransService transService;
	@Autowired
	private ITransFavService transFavService;
	@Autowired
	private ITransLibService transLibService;
	@Autowired
	private TransProcess transProcess;
	
	private String className = this.getClass().getSimpleName();
	
	/**
	 * 校验翻译许可
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public TransPermitResp canTrans(TransPermitReq req){
		TransPermitResp resp = new TransPermitResp();
		try {
			boolean canTrans = transService.canTrans(req.getToken(), req.getLang());
			resp.setPermit(canTrans ? 1 : 0);
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[canTrans]失败", e);
		}
		return resp;
	}
	
	/**
	 * 翻译，不校验接口权限，使用新接口translate
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransResp trans(TransReq req){
		TransResp resp = new TransResp();
		resp.setFrom(req.getFrom());
		resp.setTo(req.getTo());
		
		//数据校验
		if(StringUtil.isBlankOrNull(req.getFrom())){
			resp.error(RespConstants.RESPCODE_INVALID, "源语言为空");
			return resp;
		}
		if(StringUtil.isBlankOrNull(req.getTo())){
			resp.error(RespConstants.RESPCODE_INVALID, "目标语言为空");
			return resp;
		}
		if(StringUtil.isBlankOrNull(req.getSrc())){
			resp.error(RespConstants.RESPCODE_INVALID, "待翻译内容为空");
			return resp;
		}
		if(TransConfig.TRANS_MAXCHARS<req.getSrc().getBytes().length){
			resp.error(RespConstants.RESPCODE_INVALID, "待翻译内容超长");
			return resp;
		}
		
		try {
			resp.getTrans_result().addAll(transProcess.trans(req.getFrom(), req.getTo(), req.getSrc(), req.getUid()));
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[trans]失败", e);
		}
		return resp;
	}
	
	/**
	 * 翻译，校验接口权限，替换trans接口
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public TransResp translate(TransReq req){
		TransResp resp = new TransResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			resp.setFrom(req.getFrom());
			resp.setTo(req.getTo());
			
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getFrom())){
					resp.error(RespConstants.RESPCODE_INVALID, "源语言为空");
				}else if(StringUtil.isBlankOrNull(req.getTo())){
					resp.error(RespConstants.RESPCODE_INVALID, "目标语言为空");
				}else if(StringUtil.isBlankOrNull(req.getSrc())){
					resp.error(RespConstants.RESPCODE_INVALID, "待翻译内容为空");
				}else if(TransConfig.TRANS_MAXCHARS<req.getSrc().getBytes().length){
					resp.error(RespConstants.RESPCODE_INVALID, "待翻译内容超长");
				}
			}
			
			if(resp.successed()){
				resp.getTrans_result().addAll(transProcess.trans(req.getFrom(), req.getTo(), req.getSrc(), req.getUid()));
			}
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(className, methodName, reqTime, req, resp, exception);
		}
		
		return resp;
	}
	
	/** 
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Override
	public Response saveTransLog(TransLogReq req) {
		Response resp = new Response();
		try {
			if(TransConfig.TRANS_LOG_ON){
				transService.addLog(req.getToken(), req.getSrcLang(), req.getSource(), req.getTarLang(), req.getTarget(), "webservice");
			}
		} catch (Exception e) {
			resp.error();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("访问接口[saveTransLog]失败：")
				 .append(req.getSrcLang()).append("->").append(req.getTarLang())
				 .append(" ").append(req.getSource()).append(" >>> ").append(req.getTarget());
			logger.error(sbLog.toString(), e);
		}
		return resp;
	}

	/**
	 * 收藏
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Override
	public Response favTrans(TransFavAddReq req) {
		Response resp = new Response();
		try {
			transService.favTrans(req.getUid(), req.getFrom(), req.getSrc(), req.getTo(), req.getDst());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[favTrans]失败", e);
		}
		return resp;
	}
	
	/**
	 * 分页查询日常用语
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findDaily(TransFavQryReq req, String page, String pageSize){
		PageBeanResponse resp = new PageBeanResponse();
		try {
			PageBean pageBean = new PageBean(page, pageSize, null);
			transLibService.finDaily(req.getTo(), pageBean);
			resp.setPageBean(pageBean);
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[findDaily]失败", e);
		}
		return resp;
	}
	
	/**
	 * 分页查询用户收藏
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findUserFav(TransFavQryReq req, String page, String pageSize){
		PageBeanResponse resp = new PageBeanResponse();
		if(StringUtil.isBlankOrNull(req.getUid())){
			resp.error(RespConstants.RESPCODE_INVALID, "用户标识为空");
			return resp;
		}
		if(StringUtil.isBlankOrNull(req.getTo())){
			resp.error(RespConstants.RESPCODE_INVALID, "目标语言为空");
			return resp;
		}
		
		try {
			PageBean pageBean = new PageBean(page, pageSize, null);
			transFavService.findInfo(req.getUid(), req.getTo(), pageBean);
			resp.setPageBean(pageBean);
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[findUserFav]失败", e);
		}
		return resp;
	}
}
