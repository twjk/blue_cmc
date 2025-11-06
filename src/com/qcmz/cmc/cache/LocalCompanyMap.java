package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ILocalCompanyDao;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 公司缓存
 */
@Component
public class LocalCompanyMap extends AbstractCacheMap {
	@Autowired
	private ILocalCompanyDao localCompanyDao;
	
	private Map<Long, CmcLtCompany> map = null;
	private Map<Long, CmcLtCompany> systemCompanyMap = null;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcLtCompany> mapTemp = new HashMap<Long, CmcLtCompany>();
		Map<Long, CmcLtCompany> systemCompanyMapTemp = new HashMap<Long, CmcLtCompany>();
		
		List<CmcLtCompany> list = localCompanyDao.findCompanyAll();
		for (CmcLtCompany bean : list) {
			mapTemp.put(bean.getCompanyid(), bean);
			if(bean.getUserid()==null){
				systemCompanyMapTemp.put(bean.getCompanyid(), bean);
			}
		}
		map = mapTemp;
		systemCompanyMap = systemCompanyMapTemp;
	}

	/**
	 * 根据名称模糊查询公司列表
	 * @param companyName
	 * @param defaultIds 默认公司编号，多个用;分隔
	 * @return
	 * 修改历史：
	 */
	public List<CmcLtCompany> findSystemCompany(String companyName, List<Long> defaultIds){
		List<CmcLtCompany> result = new ArrayList<CmcLtCompany>(); 
		List<Long> ids = new ArrayList<Long>();
		if(!safeInit(systemCompanyMap)) return result;
		
		//默认返回
		CmcLtCompany bean = null;
		int size = 10;
		if(StringUtil.isBlankOrNull(companyName)){
			if(defaultIds!=null){
				for (Long companyId : defaultIds) {
					bean = getCompany(companyId);
					if(bean==null) continue;
					result.add(bean);
					ids.add(bean.getCompanyid());
					if(--size<=0) break;
				}
			}
			if(size>0){
				for (Long key : systemCompanyMap.keySet()) {
					if(ids.contains(key)) continue;
					bean = systemCompanyMap.get(key);
					if(bean==null) continue;
					result.add(bean);
					if(--size<=0) break;
				}
			}
			return result;
		}
		
		//完全匹配
		for (Long key : systemCompanyMap.keySet()) {
			bean = systemCompanyMap.get(key);
			if(bean.getCompanyname().equalsIgnoreCase(companyName)){
				result.add(bean);
				size--;
			}
		}
		
		//模糊匹配
		for (Long key : systemCompanyMap.keySet()) {
			bean = systemCompanyMap.get(key);
			if(bean.getCompanyname().contains(companyName) && !bean.getCompanyname().equalsIgnoreCase(companyName)){
				result.add(bean);
				if(--size<=0) break;
			}
		}
		
		return result;
	}
	
	/**
	 * 获取公司
	 * @param companyId
	 * @return
	 */
	public CmcLtCompany getCompany(Long companyId){
		if(!safeInit(map)) return null;
		return map.get(companyId);
	}
	
	/**
	 * 获取公司名称
	 * @param companyId
	 * @return
	 */
	public String getCompanyName(Long companyId){
		CmcLtCompany company = getCompany(companyId);
		if(company!=null){
			return company.getCompanyname();
		}
		return null;
	}
	
	/**
	 * 根据名称获取公司信息
	 * @param companyName
	 * @return
	 */
	public CmcLtCompany getSystemCompany(String companyName){
		if(!safeInit(systemCompanyMap)) return null;
		if(StringUtil.isBlankOrNull(companyName)) return null;
		
		CmcLtCompany company = null;
		for (Long key : systemCompanyMap.keySet()) {
			company = systemCompanyMap.get(key);
			if(company.getCompanyname().equals(companyName)){
				return company; 
			}
		}
		
		return null;
	}
	
	@Override
	public void update(Object obj) {
		if(obj==null || map==null || map.isEmpty()) return;
		CmcLtCompany bean = (CmcLtCompany)obj;
		map.put(bean.getCompanyid(), bean);
		systemCompanyMap.put(bean.getCompanyid(), bean);
	}

	@Override
	public void delete(Object obj) {
		if(obj==null || map==null || map.isEmpty()) return;
		Long id = (Long)obj;
		map.remove(id);
		systemCompanyMap.remove(id);
	}
}
