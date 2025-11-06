package com.qcmz.cmc.ws.provide.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransPicLangMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.process.TransPicProcess;
import com.qcmz.cmc.process.TransPriceProcess;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderLogBean;
import com.qcmz.cmc.ws.provide.vo.TransPicAddReq;
import com.qcmz.cmc.ws.provide.vo.TransPicBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDetailResp;
import com.qcmz.cmc.ws.provide.vo.TransPicLangBean;
import com.qcmz.cmc.ws.provide.vo.TransPicLangQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicLangResp;
import com.qcmz.cmc.ws.provide.vo.TransPicPriceQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicPriceResp;
import com.qcmz.cmc.ws.provide.vo.TransPicQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicQueryResp;
import com.qcmz.cmc.ws.provide.vo.TransPicToHumanReq;
import com.qcmz.cmc.ws.provide.vo.TransPriceResp;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：图片翻译接口
 * 修改历史：
 */
@Component
public class TransPicInterface extends BaseInterface {
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private TransPicProcess transPicProcess;
	@Autowired
	private TransPriceProcess transPriceProcess;
	
	@Autowired
	private TransPicLangMap transPicLangMap;
	
	/**
	 * 获取图片翻译支持的语言列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPicLangResp findLang(TransPicLangQueryReq req, String interfaceType, String reqIp){
		TransPicLangResp resp = new TransPicLangResp();
		try {
			//身份验证
			if(req.getUid()==null){
				SrmClient.validAccount(req, resp, className);
			}else{
				SrmClient.validAccount(req, resp, className, req.getUid());
			}
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.setResultList(transPicLangMap.findLang());
				if(VersionUtil.compat1(SrmClient.getServiceType(req.getAuthAccount())
						, SrmClient.getPlatform(req.getAuthAccount())
						,req.getAppVer())){
					//机器翻译只保留灵云支持语言
					List<TransPicLangBean> list = new ArrayList<TransPicLangBean>();
					for (TransPicLangBean bean : resp.getResultList()) {
						if(DictConstant.DICT_TRANSWAY_MACHINE.equals(bean.getTransWay())
								&& !DictConstants.DICT_LANG_ZHCN.equals(bean.getFrom()) 
								&& !DictConstants.DICT_LANG_EN.equals(bean.getFrom())){
							continue;
						}
						list.add(bean);
					}
					resp.setResultList(list);
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
	 * 分页查询用户图片翻译列表
	 * 20171123之前独立的图片翻译订单
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransPicQueryResp findPic(TransPicQueryReq req, String pageSize, String interfaceType, String reqIp){
		TransPicQueryResp resp = new TransPicQueryResp();
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
				List<TransPicBean> list = transPicService.findPic(req.getUid(), pageBean.getPageSize(), req.getLastPicId());
				
				//兼容处理
				if(VersionUtil.compat1(SrmClient.getServiceType(req.getAuthAccount())
						, SrmClient.getPlatform(req.getAuthAccount())
						,req.getAppVer())){
					for (TransPicBean bean : list) {
						bean.setDealStatus(OrderUtil.dealStatusCompat(bean.getDealStatus()));
					}
				}
				
				resp.setResultList(list);
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取图片翻译详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPicDetailResp getPic(OrderGetReq req, String interfaceType, String reqIp){
		TransPicDetailResp resp = new TransPicDetailResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				TransPicDetailBean bean = transPicService.getPicDetail(req.getUid(), req.getOrderId());
				
				//兼容
				if(VersionUtil.compat1(SrmClient.getServiceType(req.getAuthAccount())
						, SrmClient.getPlatform(req.getAuthAccount())
						,req.getAppVer())){
					//状态
					bean.setDealStatus(OrderUtil.dealStatusCompat(bean.getDealStatus()));
					//去除未操作的日志
					Iterator<OrderLogBean> it = bean.getLogs().iterator();
					OrderLogBean log = null; 
					while(it.hasNext()){
						log = it.next();
						if(log.getOperTime()==null){
							it.remove();
						}
					}
				}
				
				resp.setBean(bean);
			}
		} catch (ParamException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 添加图片翻译
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderCreateResp addPic(TransPicAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getUserType()==null){
					resp.errorParam(ExceptionConstants.MSG_USERTYPE_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTransWay())){
					resp.errorParam("翻译途径为空");
				}else if(req.getBean().getFile()==null){
					resp.errorParam("图片文件为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam(ExceptionConstants.MSG_TOLANG_EMPTY);
				}else if(StringUtil.notBlankAndNull(req.getBean().getSrc())
						&& req.getBean().getSrc().length()>4000){
					resp.errorData("原文超长");
				}else if(StringUtil.notBlankAndNull(req.getBean().getDst())
						&& req.getBean().getDst().length()>4000){
					resp.errorData("译文超长");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				OrderCreateResult r = transPicProcess.addPic(req.getBean(), serviceType, platform, req.getAppVer());
				BeanUtil.copyProperties(resp, r);
				resp.setPicId(r.getOrderId());
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (SystemException e) {
			resp.error(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 转为人工翻译
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response toHuman(TransPicToHumanReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getPicId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				transPicProcess.toHuman(req.getUid(), req.getPicId(), req.getRequirement());
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取图片翻译价格
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransPicPriceResp getPrice(TransPicPriceQueryReq req, String interfaceType, String reqIp){
		TransPicPriceResp resp = new TransPicPriceResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transPriceProcess.getTransPicPrice(req.getLanguage()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取图片翻译价格
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPriceResp findPrice(TransPicPriceQueryReq req, String interfaceType, String reqIp){
		TransPriceResp resp = new TransPriceResp();
		try {
			if(VersionUtil.compat5(SrmClient.getServiceType(req.getAuthAccount()), SrmClient.getPlatform(req.getAuthAccount()), req.getAppVer(), req.getBean().getFrom())){
				resp.errorBusiness("当前版本不支持英语图片翻译");
				return resp;
			}
			
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transPriceProcess.findTransPrice(DictConstants.DICT_TRANSTYPE_PIC, req.getBean().getTransWay()
						, req.getBean().getSrc(), req.getBean().getFrom(), req.getBean().getTo(), req.getLanguage()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
