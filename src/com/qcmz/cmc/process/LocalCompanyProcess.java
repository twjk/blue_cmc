package com.qcmz.cmc.process;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.cmc.service.ILocalCompanyService;
import com.qcmz.cmc.thrd.LocalCompanyCertifyNotifyThrd;
import com.qcmz.cmc.util.LocalTaskUtil;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBean;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBusinessLicenseUploadBean;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBusinessLicenseUploadResult;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyHRSaveBean;
import com.qcmz.cmc.ws.provide.vo.LocalCompanySaveBean;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class LocalCompanyProcess {
	@Autowired
	private ILocalCompanyService localCompanyService;
	
	/**
	 * 获取公司信息
	 * @param userId
	 * @return
	 */
	public LocalCompanyBean getCompany(Long userId){
		CmcLtCompany entity = localCompanyService.getCompanyByUserId(userId);
		if(entity==null) return null;
		
		LocalCompanyBean bean = new LocalCompanyBean();
		bean.setCompanyId(entity.getCompanyid());
		bean.setCompanyName(entity.getCompanyname());
		bean.setUscc(entity.getUscc());
		bean.setContacts(entity.getContacts());
		bean.setTel(entity.getTel());
		bean.setBusinessLicense(entity.getBusinesslicense());
		bean.setCertifyStatus(entity.getCertifystatus());
		bean.setCertifyResult(entity.getCertifyresult());
		bean.setHr(entity.getHr());
		bean.setHrTel(entity.getHrtel());
		
		return bean;
	}
	
	/**
	 * 上传营业执照图片
	 * @param bean
	 * @return
	 * @exception DataException、SystemException
	 */
	public LocalCompanyBusinessLicenseUploadResult uploadBusinessLicense(LocalCompanyBusinessLicenseUploadBean bean){
		File file = bean.getFile();
		//校验文件类型
		String fileType = FileTypeUtil.getImageType(file);
		if(fileType==null){
			throw new DataException("文件类型有误");
		}
		
		//上传
		String dirPath = LocalTaskUtil.getCompanyBasePath();
		String url = FileServiceUtil.saveOrUploadFile(file, dirPath, FilePathUtil.newFileName(fileType));
		if(StringUtil.isBlankOrNull(url)){
			throw new SystemException("文件保存失败");
		}
		
		LocalCompanyBusinessLicenseUploadResult result = new LocalCompanyBusinessLicenseUploadResult();
		result.setUrl(url);
		return result;
	}
	
	/**
	 * 保存公司信息
	 * @param bean
	 */
	public void saveCompany(LocalCompanySaveBean bean){
		CmcLtCompany entity = localCompanyService.getCompanyByUserId(bean.getUid());
		if(entity==null){
			entity = new CmcLtCompany();
			entity.setUserid(bean.getUid());
			entity.setCreateway(DictConstant.DICT_LOCALCOMPANY_CREATEWAY_COMPANY);
		}
		entity.setCompanyname(bean.getCompanyName());
		entity.setUscc(bean.getUscc());
		entity.setContacts(bean.getContacts());
		entity.setTel(bean.getTel());
		entity.setBusinesslicense(bean.getBusinessLicense());
		if(DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_FAIL.equals(entity.getCertifystatus())){
			entity.setCertifystatus(DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_UNCERTIFY);
			entity.setCertifyresult("");
		}
		localCompanyService.saveOrUpdate(entity);
		
		CacheSynProcess.synUpdateCacheThrd(BeanConstant.BEANID_CACHE_LOCALCOMPANY, entity);
	}
	
	/**
	 * 保存招聘联系信息
	 * @param bean
	 */
	public void saveHR(LocalCompanyHRSaveBean bean){
		CmcLtCompany entity = localCompanyService.getCompanyByUserId(bean.getUid());
		if(entity==null){
			entity = new CmcLtCompany();
			entity.setUserid(bean.getUid());
			entity.setCreateway(DictConstant.DICT_LOCALCOMPANY_CREATEWAY_COMPANY);
		}
		entity.setHr(bean.getHr());
		entity.setHrtel(bean.getHrTel());
		localCompanyService.saveOrUpdate(entity);
		
		CacheSynProcess.synUpdateCacheThrd(BeanConstant.BEANID_CACHE_LOCALCOMPANY, entity);
	}
	
	/**
	 * 提交企业认证审核
	 * @param userId
	 */
	public void submitCertify(Long userId){
		CmcLtCompany company = localCompanyService.getCompanyByUserId(userId);
		if(company==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		//状态验证
		if(!DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_UNCERTIFY.equals(company.getCertifystatus())
				&& !DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_FAIL.equals(company.getCertifystatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		//信息验证
		if(StringUtil.isBlankOrNull(company.getCompanyname())
				|| StringUtil.isBlankOrNull(company.getUscc())
				|| StringUtil.isBlankOrNull(company.getContacts())
				|| StringUtil.isBlankOrNull(company.getTel())
				|| StringUtil.isBlankOrNull(company.getBusinesslicense())){
			throw new DataException("企业信息不完整");
		}
		
		
		localCompanyService.updateCertifyStatus(company.getCompanyid(), DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_VERIFYING, "");
		
		//通知
		String msg = "企业认证待审核："+company.getCompanyname();
		MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, msg, msg);
	}
	
	/**
	 * 通过认证
	 * @param companyId
	 */
	public void passCertify(Long companyId){
		CmcLtCompany company = localCompanyService.getCompany(companyId);
		
		company.setCertifystatus(DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_CERTIFIED);
		company.setCertifyresult("");
		localCompanyService.saveOrUpdate(company);
		
		//通知用户
		LocalCompanyCertifyNotifyThrd.notifyCertifyResult(company.getUserid()
				, company.getCompanyname(), DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_CERTIFIED);
	}
	
	/**
	 * 驳回认证
	 * @param companyId
	 * @param reason
	 */
	public void rejectCertify(Long companyId, String reason){
		CmcLtCompany company = localCompanyService.getCompany(companyId);
		
		company.setCertifystatus(DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_FAIL);
		company.setCertifyresult(reason);
		localCompanyService.saveOrUpdate(company);
		
		//通知用户
		LocalCompanyCertifyNotifyThrd.notifyCertifyResult(company.getUserid()
				, company.getCompanyname(), DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_FAIL);
	}
	
	/**
	 * 采集添加公司
	 * @param companyName
	 * @return
	 */
	public CmcLtCompany addCompany4Spider(String companyName){
		CmcLtCompany bean = new CmcLtCompany(companyName);
		bean.setCreateway(DictConstant.DICT_LOCALCOMPANY_CREATEWAY_SPD);
		
		CmcLtCompany entity = localCompanyService.saveOrUpdate4System(bean);
		
		CacheSynProcess.synUpdateCacheThrd(BeanConstant.BEANID_CACHE_LOCALCOMPANY, entity);
		
		return entity;
	}
}
