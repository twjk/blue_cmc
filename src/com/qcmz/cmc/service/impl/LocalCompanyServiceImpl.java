package com.qcmz.cmc.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILocalCompanyDao;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.cmc.service.ILocalCompanyService;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;

@Service
public class LocalCompanyServiceImpl implements ILocalCompanyService {
	@Autowired
	private ILocalCompanyDao localCompanyDao;
	
	/**
	 * 分页获取公司列表
	 * @param map
	 * @param pageBean<CmcLtCompany>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		localCompanyDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取公司信息
	 * @param companyId
	 * @return
	 */
	public CmcLtCompany getCompany(Long companyId){
		return (CmcLtCompany) localCompanyDao.load(CmcLtCompany.class, companyId);
	}
	
	/**
	 * 根据名称获取系统公司信息
	 * @param companyName
	 * @return
	 */
	public CmcLtCompany getSystemCompanyByName(String companyName){
		return localCompanyDao.getSystemCompanyByName(companyName);
	}
	
	/**
	 * 获取用户公司信息
	 * @param userId
	 * @return
	 */
	public CmcLtCompany getCompanyByUserId(Long userId){
		return localCompanyDao.getCompanyByUserId(userId);
	}
	
	/**
	 * 保存更新公司信息
	 * @param bean
	 * @return
	 */
	public void saveOrUpdate(CmcLtCompany bean){
		localCompanyDao.saveOrUpdate(bean);
	}
	
	/**
	 * 保存更新公司信息
	 * @param bean
	 * @return
	 */
	public CmcLtCompany saveOrUpdate4System(CmcLtCompany bean){
		if(bean.getUserid()==null) bean.setUserid(0L);
		
		if(bean.getUserid().equals(0L)
				&& localCompanyDao.isRepeat(bean.getUserid(), bean.getCompanyname(), bean.getCompanyid())){
			throw new DataException("公司已经存在");
		}
		if(bean.getCompanyid()==null){
			localCompanyDao.save(bean);
			return bean;
		}else{
			CmcLtCompany entity = getCompany(bean.getCompanyid());
			entity.setCompanyname(bean.getCompanyname());
			entity.setLogo(bean.getLogo());
			entity.setIntroduce(bean.getIntroduce());
			entity.setUscc(bean.getUscc());
			entity.setContacts(bean.getContacts());
			entity.setTel(bean.getTel());
			entity.setHr(bean.getHr());
			entity.setHrtel(bean.getHrtel());
			localCompanyDao.update(entity);
			return entity;
		}
	}
	
	/**
	 * 更新认证状态
	 * @param companyId
	 * @param certifyStatus
	 * @param certifyResult
	 */
	public void updateCertifyStatus(Long companyId, Integer certifyStatus, String certifyResult){
		localCompanyDao.updateCertifyStatus(companyId, certifyStatus, certifyResult);
	}
	
	/**
	 * 删除公司
	 * @param companyId
	 * @exception DataException
	 */
	public void deleteCompany(Long companyId){
		if(localCompanyDao.isReferences(companyId)){
			throw new DataException("公司已经被引用");
		}
		localCompanyDao.delete(CmcLtCompany.class, companyId);
	}
}
