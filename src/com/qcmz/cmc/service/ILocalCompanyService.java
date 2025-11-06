package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;

public interface ILocalCompanyService {
	/**
	 * 分页获取公司列表
	 * @param map
	 * @param pageBean<CmcLtCompany>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取公司信息
	 * @param companyId
	 * @return
	 */
	public CmcLtCompany getCompany(Long companyId);
	/**
	 * 根据名称获取系统公司信息
	 * @param companyName
	 * @return
	 */
	public CmcLtCompany getSystemCompanyByName(String companyName);
	/**
	 * 获取用户公司信息
	 * @param userId
	 * @return
	 */
	public CmcLtCompany getCompanyByUserId(Long userId);
	/**
	 * 保存更新公司信息
	 * @param bean
	 * @return
	 */
	public void saveOrUpdate(CmcLtCompany bean);
	/**
	 * 保存更新系统公司信息
	 * @param bean
	 * @return
	 */
	public CmcLtCompany saveOrUpdate4System(CmcLtCompany bean);
	/**
	 * 更新认证状态
	 * @param companyId
	 * @param certifyStatus
	 * @param certifyResult
	 */
	public void updateCertifyStatus(Long companyId, Integer certifyStatus, String certifyResult);
	/**
	 * 删除公司
	 * @param companyId
	 * @exception DataException
	 */
	public void deleteCompany(Long companyId);
}
