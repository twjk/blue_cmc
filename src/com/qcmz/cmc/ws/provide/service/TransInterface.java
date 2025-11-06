package com.qcmz.cmc.ws.provide.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.AppUserForbidMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.process.TransCorrectProcess;
import com.qcmz.cmc.process.TransProcess;
import com.qcmz.cmc.service.ITransService;
import com.qcmz.cmc.service.ITransUserCorrectService;
import com.qcmz.cmc.thrd.TransLogThrd;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.TransCorrectQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransCorrectQueryResp;
import com.qcmz.cmc.ws.provide.vo.TransLangBean;
import com.qcmz.cmc.ws.provide.vo.TransLangResp;
import com.qcmz.cmc.ws.provide.vo.TransLogAddBean;
import com.qcmz.cmc.ws.provide.vo.TransLogReq;
import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransResp;
import com.qcmz.cmc.ws.provide.vo.TransRespV2;
import com.qcmz.cmc.ws.provide.vo.TransResult;
import com.qcmz.cmc.ws.provide.vo.TransUserCorrectReq;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：翻译接口实现
 * 修改历史：
 */
@Component
public class TransInterface extends BaseInterface{
	
	@Autowired
	private ITransService transService;
	@Autowired
	private ITransUserCorrectService transUserCorrectService;
	
	@Autowired
	private TransProcess transProcess;
	@Autowired
	private AppUserForbidMap appUserForbidMap;
	@Autowired
	private TransCorrectProcess transCorrectProcess;
	
	private Logger loggerForbid = Logger.getLogger("ForbidTrans");
	
	/**
	 * 获取翻译语言列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransLangResp findLang(Request req, String interfaceType, String reqIp){
		TransLangResp resp = new TransLangResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				List<TransLangBean> list = transService.findLang();
				if(VersionUtil.compat2(serviceType, platform, req.getAppVer())){
					for (TransLangBean bean : list) {
						if(ArrayUtil.contain(VersionUtil.LANGS, bean.getLangCode())){
							resp.getResultList().add(bean);
						}
					}
				}if(VersionUtil.compat3(serviceType, platform, req.getAppVer())){
					for (TransLangBean bean : list) {
						if(ArrayUtil.contain(VersionUtil.LANGS, bean.getLangCode())
								|| ArrayUtil.contain(VersionUtil.LANGS_4, bean.getLangCode())){
							resp.getResultList().add(bean);
						}
					}
				}else if(DictConstants.DICT_PLATFORM_IOS.equals(platform)){
					for (TransLangBean bean : list) {
						if(!ArrayUtil.contain(VersionUtil.LANGS_UNSUPPORT_IOS, bean.getLangCode())){
							resp.getResultList().add(bean);
						}
					}
				}else{
					resp.getResultList().addAll(list);
				}
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 翻译，不校验接口权限
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransResp trans(TransReq req, String interfaceType, String reqIp){
		TransResp resp = new TransResp();
		resp.setFrom(req.getBean().getFrom());
		resp.setTo(req.getBean().getTo());
		try {
			//数据校验
			if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
				resp.errorParam("源语言为空");
			}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
				resp.errorParam("目标语言为空");
			}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
				resp.errorData("待翻译内容为空");
			}else if(TransConfig.TRANS_MAXCHARS<req.getBean().getSrc().getBytes().length){
				resp.errorData("待翻译内容超长");
			}
			
			if(resp.successed()){
				if(SystemConfig.TRANSWS_TRANS_SWITCH){
					String platform = SrmClient.getPlatform(req.getAuthAccount());
					String serviceType = SrmClient.getServiceType(req.getAuthAccount());
					resp.getTrans_result().add(transProcess.trans(req.getBean(), serviceType, platform, req.getAppVer(), reqIp));
				}else{
					TransResult bean = new TransResult();
					bean.setFrom(req.getBean().getFrom());
					bean.setSrc(req.getBean().getSrc());
					bean.setTo(req.getBean().getTo());
					bean.setDst("请更新版本");
					bean.setSimilar(0);
					bean.setProxyid(0);
					resp.getTrans_result().add(bean);
				}
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 翻译，校验接口权限，替换trans接口
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransResp translate(TransReq req, String interfaceType, String reqIp){
		TransResp resp = new TransResp();
		try {
			resp.setFrom(req.getBean().getFrom());
			resp.setTo(req.getBean().getTo());
			
			if(appUserForbidMap.isForbid(req.getBean().getUid(), req.getBean().getUuid())){
				loggerForbid.info(StringUtil.toString(req));
			}
			
			//身份验证
			String platform = SrmClient.getPlatform(req.getAuthAccount());
			String serviceType = SrmClient.getServiceType(req.getAuthAccount());
			
			if(VersionUtil.compat6(serviceType, platform, req.getAppVer())){
				SrmClient.validAccount(req, resp, className);
			}else{
				if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorData("待翻译内容为空");
				}else{
					SrmClient.validAccount(req, resp, className, SecretUtil.encryptByMd5(req.getBean().getSrc()));
				}
			}
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam("源语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam("目标语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorData("待翻译内容为空");
				}else if(TransConfig.TRANS_MAXCHARS<req.getBean().getSrc().getBytes().length){
					resp.errorData("待翻译内容超长");
				}
			}
			
			if(resp.successed()){
				resp.getTrans_result().add(transProcess.trans(req.getBean(), serviceType, platform, req.getAppVer(), reqIp));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 翻译
	 * @param req
	 * @return
	 * 修改历史：20180126
	 */
	public TransRespV2 translateV2(TransReq req, String interfaceType, String reqIp){
		TransRespV2 resp = new TransRespV2();
		try {
			if(appUserForbidMap.isForbid(req.getBean().getUid(), req.getBean().getUuid())){
				loggerForbid.info(StringUtil.toString(req));
			}
			
			//身份验证
			String platform = SrmClient.getPlatform(req.getAuthAccount());
			String serviceType = SrmClient.getServiceType(req.getAuthAccount());
			
			if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
				resp.errorData("待翻译内容为空");
			}else{
				SrmClient.validAccount(req, resp, className, SecretUtil.encryptByMd5(req.getBean().getSrc()));
			}
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam("源语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam("目标语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorData("待翻译内容为空");
				}else if(TransConfig.TRANS_MAXCHARS<req.getBean().getSrc().getBytes().length){
					resp.errorData("待翻译内容超长");
				}
			}
			
			if(resp.successed()){
				resp.setResult(transProcess.transV2(req.getBean(), serviceType, platform, req.getAppVer(), reqIp));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/** 
	 * 保存翻译日志
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public Response saveTransLog(TransLogReq req, String interfaceType, String reqIp) {
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam("源语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorParam("原文为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam("目标语言为空");
				}else if(req.getBean().getProxyId()==null){
					resp.errorParam("代理编号为空");
				}else if(req.getBean().getStartTime()==null){
					resp.errorParam("翻译开始时间为空");
				}else if(req.getBean().getEndTime()==null){
					resp.errorParam("翻译结束时间为空");
				}
			}
			
			if(resp.successed()){
				Integer similar = SystemConstants.VALUE_0;
				boolean transSuccess = true;
				boolean hitCache = false;
				String resultDesc = "webservice";
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				
				TransLogAddBean bean = req.getBean();				
				TransLogThrd.start(bean.getUid(), bean.getPushRegid(), bean.getUuid(), bean.getTmcode(), bean.getTransType(), bean.getSid()
						, bean.getProxyId(), bean.getFrom(), bean.getSrc(), bean.getTo(), bean.getDst()
						, similar, transSuccess, hitCache, resultDesc, bean.getStartTime(), bean.getEndTime()
						, reqIp, bean.getLon(), bean.getLat(), platform, req.getAppVer(), serviceType);
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败：", e);
		}

		return resp;
	}
	
	/**
	 * 用户纠错
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response userCorrect(TransUserCorrectReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getSid())){
					resp.errorParam("会话标识为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam("源语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorParam("原文为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam("目标语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getDst())){
					resp.errorParam("修正译文为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getMtDst())){
					resp.errorParam("机器翻译译文为空");
				}
			}
			
			if(resp.successed()){
				transUserCorrectService.saveUserCorrect(req.getBean());
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败：", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取修正翻译列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransCorrectQueryResp findCorrectTrans(TransCorrectQueryReq req, String interfaceType, String reqIp){
		TransCorrectQueryResp resp = new TransCorrectQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUuid());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getUuid())){
					resp.errorParam("用户标识为空");
				}
			}
			
			if(resp.successed()){
				resp.setResult(transCorrectProcess.findCorrect(req.getUuid()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败：", e);
		}
		
		return resp;
	}
}
