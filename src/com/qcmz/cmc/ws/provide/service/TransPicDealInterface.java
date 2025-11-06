package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransPicProcess;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicCompleteReq;
import com.qcmz.cmc.ws.provide.vo.TransPicDealDetailResp;
import com.qcmz.cmc.ws.provide.vo.TransPicDealGetReq;
import com.qcmz.cmc.ws.provide.vo.TransPicDealQueryResp;
import com.qcmz.cmc.ws.provide.vo.TransPicFinishReq;
import com.qcmz.cmc.ws.provide.vo.TransPicRotateReq;
import com.qcmz.cmc.ws.provide.vo.TransPicRotateResp;
import com.qcmz.cmc.ws.provide.vo.TransPicTransBean;
import com.qcmz.cmc.ws.provide.vo.TransPicTransReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：图片人工处理接口
 * 修改历史：
 */
@Component
public class TransPicDealInterface extends BaseInterface {
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private TransPicProcess transPicProcess;
	
	/**
	 * 分页获取人工翻译列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPicDealQueryResp findPic(OrderDealQueryReq req, String pageSize, String interfaceType, String reqIp){
		TransPicDealQueryResp resp = new TransPicDealQueryResp();
		try {
			//版本校验
			String platform = SrmClient.getPlatform(req.getAuthAccount());
			if(VersionUtil.isForbidden4Cs(platform, req.getAppVer())){
				resp.errorData("请更新到最新版本");
			}
			
			//身份验证
			if(resp.successed()){
				if(VersionUtil.isVerifyOperator4TransPicDeal(platform, req.getAppVer())){
					SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
					req.getBean().setOperator(null);				
				}else{
					SrmClient.validAccount(req, resp, className);
				}
			}
			
			//数据校验
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(transPicProcess.findPic4Deal(req.getBean(), pageBean.getPageSize()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 分页获取捡漏图片翻译列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPicDealQueryResp findPickPic(OrderDealQueryReq req, String pageSize, String interfaceType, String reqIp){
		TransPicDealQueryResp resp = new TransPicDealQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){}
			
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(transPicService.findPic4PickDeal(req.getBean(), pageBean.getPageSize()));
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
	public TransPicDealDetailResp getPic(TransPicDealGetReq req, String interfaceType, String reqIp){
		TransPicDealDetailResp resp = new TransPicDealDetailResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())){
					resp.errorParam("操作员为空");
				}else if(StringUtil.isBlankOrNull(req.getPicId())){
					resp.errorParam("图片编号为空");
				}
				
				if(StringUtil.isBlankOrNull(req.getOperator())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getPicId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transPicService.getPicDetail4Deal(req.getPicId()));
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
	 * 保存翻译结果
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response saveTrans(TransPicTransReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
				for (TransPicTransBean trans : req.getBean().getTrans()) {
					if(StringUtil.isBlankOrNull(trans.getDst())){
						resp.errorParam("译文为空");
						break;
					}
				}
			}
			
			//处理
			if(resp.successed()){
				transPicService.saveTrans(req.getBean());
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 保存翻译结果
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response savePickTrans(TransPicTransReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
				for (TransPicTransBean trans : req.getBean().getTrans()) {
					if(StringUtil.isBlankOrNull(trans.getDst())){
						resp.errorParam("译文为空");
						break;
					}
				}
			}
			
			//处理
			if(resp.successed()){
				transPicService.savePickTrans(req.getBean());
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 翻译完成
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response completeTrans(TransPicCompleteReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
				for (TransPicTransBean trans : req.getBean().getTrans()) {
					if(StringUtil.isBlankOrNull(trans.getDst())){
						resp.errorParam("译文为空");
						break;
					}
				}
			}
			
			//处理
			if(resp.successed()){
				transPicService.completeTrans(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 完成翻译
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response finishTrans(TransPicFinishReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
				for (TransPicTransBean trans : req.getBean().getTrans()) {
					if(StringUtil.isBlankOrNull(trans.getDst())){
						resp.errorParam("译文为空");
						break;
					}
				}
			}
			
			//处理
			if(resp.successed()){
				transPicProcess.finishTrans(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 旋转图片
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPicRotateResp rotatePic(TransPicRotateReq req, String interfaceType, String reqIp){
		TransPicRotateResp resp = new TransPicRotateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getPicId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(req.getDegree()==null){
					resp.errorParam("旋转度数为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transPicService.rotatePic(req.getPicId(), req.getDegree(), new Operator(req.getOperator(), req.getOperatorName(), reqIp)));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
}
