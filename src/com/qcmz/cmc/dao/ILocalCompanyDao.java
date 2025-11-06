package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ILocalCompanyDao extends IBaseDAO {
	/**
	 * 分页获取公司列表
	 * @param map
	 * @param pageBean<CmcLtCompany>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取所有公司
	 * @return
	 */
	public List<CmcLtCompany> findCompanyAll();
	/**
	 * 获取系统公司信息
	 * @param userId
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
	 * 公司名称是否重复
	 * @param userId not null
	 * @param companyName not null
	 * @param companyId 
	 * @return
	 */
	public boolean isRepeat(Long userId, String companyName, Long companyId);
	/**
	 * 公司是否被引用
	 * @param companyId
	 * @return
	 */
	public boolean isReferences(Long companyId);
	/**
	 * 更新认证状态
	 * @param companyId
	 * @param certifyStatus
	 * @param certifyResult
	 */
	public void updateCertifyStatus(Long companyId, Integer certifyStatus, String certifyResult);
}
