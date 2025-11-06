package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.LocalCompanyInterface;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBusinessLicenseUploadReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyCertifySubmitReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyHRSaveReq;
import com.qcmz.cmc.ws.provide.vo.LocalCompanySaveReq;
import com.qcmz.framework.action.BaseWSAction;

public class LocalCompanyWSAction extends BaseWSAction {
	@Autowired
	private LocalCompanyInterface localCompanyInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 统一社会信用代码
	 */
	private String uscc;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 营业执照
	 */
	private String businessLicense;
	/**
	 * 文件
	 */
	private File file;
	/**
	 * 招聘联系人
	 */
	private String hr;
	/**
	 * 招聘联系人电话
	 */
	private String hrTel;
	
	
	/**
	 * 上传营业执照文件
	 */
	public String uploadBusinessLicense(){
		LocalCompanyBusinessLicenseUploadReq req = new LocalCompanyBusinessLicenseUploadReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setFile(file);
		
		jsonObj = localCompanyInterface.uploadBusinessLicense(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 保存公司信息
	 * @return
	 */
	public String saveCompany(){
		LocalCompanySaveReq req = new LocalCompanySaveReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setCompanyName(companyName);
		req.getBean().setUscc(uscc);
		req.getBean().setContacts(contacts);
		req.getBean().setTel(tel);
		req.getBean().setBusinessLicense(businessLicense);
		
		jsonObj = localCompanyInterface.saveCompany(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 保存招聘联系信息
	 * @return
	 */
	public String saveHR(){
		LocalCompanyHRSaveReq req = new LocalCompanyHRSaveReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setHr(hr);
		req.getBean().setHrTel(hrTel);
		
		jsonObj = localCompanyInterface.saveHR(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 提交认证
	 * @return
	 */
	public String submitCertify(){
		LocalCompanyCertifySubmitReq req = new LocalCompanyCertifySubmitReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		
		jsonObj = localCompanyInterface.submitCertify(req, interfaceType, getRemoteAddr());
		
		return JSON;
	} 
	
	/**
	 * 获取用户公司
	 * @return
	 */
	public String getCompany(){
		LocalCompanyGetReq req = new LocalCompanyGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		
		jsonObj = localCompanyInterface.getCompany(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUscc() {
		return uscc;
	}

	public void setUscc(String uscc) {
		this.uscc = uscc;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getHrTel() {
		return hrTel;
	}

	public void setHrTel(String hrTel) {
		this.hrTel = hrTel;
	}
}
