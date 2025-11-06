package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.LocalCompanyProcess;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBusinessLicenseUploadReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBusinessLicenseUploadResp;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyCertifySubmitReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyGetResp;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyHRSaveReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanySaveReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 公司相关接口
 */
@Component
public class LocalCompanyInterface extends BaseInterface {
	@Autowired
	private LocalCompanyProcess localCompanyProcess;
	
	/**
	 * 上传营业执照文件
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalCompanyBusinessLicenseUploadResp uploadBusinessLicense(LocalCompanyBusinessLicenseUploadReq req, String interfaceType, String reqIp){
		LocalCompanyBusinessLicenseUploadResp resp = new LocalCompanyBusinessLicenseUploadResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getFile()==null){
					resp.errorParam(ExceptionConstants.MSG_FILE_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(localCompanyProcess.uploadBusinessLicense(req.getBean()));
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
	 * 保存公司信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response saveCompany(LocalCompanySaveReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getCompanyName())){
					resp.errorParam("公司名称为空");
				}
			}
			
			//处理
			if(resp.successed()){
				localCompanyProcess.saveCompany(req.getBean());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 保存招聘联系信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response saveHR(LocalCompanyHRSaveReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getHr())){
					resp.errorParam("联系人为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getHrTel())){
					resp.errorParam("联系电话为空");
				}
			}
			
			//处理
			if(resp.successed()){
				localCompanyProcess.saveHR(req.getBean());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 企业认证提交审核
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response submitCertify(LocalCompanyCertifySubmitReq req, String interfaceType, String reqIp){
		Response resp = new Response();
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
				localCompanyProcess.submitCertify(req.getUid());
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
	 * 获取公司详情
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalCompanyGetResp getCompany(LocalCompanyGetReq req, String interfaceType, String reqIp){
		LocalCompanyGetResp resp = new LocalCompanyGetResp();
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
				resp.setResult(localCompanyProcess.getCompany(req.getUid()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
}
