package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILocalCompanyDao;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class LocalCompanyDao extends BaseDAO implements ILocalCompanyDao {
	/**
	 * 分页获取公司列表
	 * @param map
	 * @param pageBean<CmcLtCompany>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcLtCompany t ");
		StringBuilder sbCountHql = new StringBuilder("select count(t.companyid) from CmcLtCompany t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			
			//名称
			String param = map.get("companyname");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and t.companyname like :companyname");
				params.put("companyname", "%"+param+"%");
			}
			param = map.get("userid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(param));	
			}
			param = map.get("certifystatus");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.certifystatus=:certifystatus");
				params.put("certifystatus", Integer.valueOf(param));	
			}
			param = map.get("createway");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.createway=:createway");
				params.put("createway", Integer.valueOf(param));	
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.companyid desc");
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取所有公司
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcLtCompany> findCompanyAll(){
		return (List<CmcLtCompany>) qryList("from CmcLtCompany");
	}
	
	/**
	 * 获取系统公司信息
	 * @param userId
	 * @return
	 */
	public CmcLtCompany getSystemCompanyByName(String companyName){
		String hql = "from CmcLtCompany where companyname=:companyname and userid=0";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyname", companyName);
		return (CmcLtCompany) load(hql, params);
	}
	
	/**
	 * 获取用户公司信息
	 * @param userId
	 * @return
	 */
	public CmcLtCompany getCompanyByUserId(Long userId){
		String hql = "from CmcLtCompany where userid=:userid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		return (CmcLtCompany) load(hql, params);
	}
	
	/**
	 * 公司名称是否重复
	 * @param userId not null
	 * @param companyName not null
	 * @param companyId 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isRepeat(Long userId, String companyName, Long companyId){
		StringBuilder sbHql = new StringBuilder("select companyid from CmcLtCompany where userid=:userid and companyname=:companyname");
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("userid", userId);
		 params.put("companyname", companyName);
		 
		 if(companyId!=null){
			 sbHql.append(" and companyid!=:companyid");
			 params.put("companyid", companyId);
		 }
		 
		 List<Long> list = (List<Long>) qryListTop(sbHql.toString(), params, 1);
		 return !list.isEmpty();
	}
	
	/**
	 * 公司是否被引用
	 * @param companyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isReferences(Long companyId){
		String hql = "select taskid from CmcLtTask where companyid=:companyid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyid", companyId);
		
		List<Long> list = (List<Long>) qryListTop(hql, params, 1);
		
		return !list.isEmpty();
	}
	
	/**
	 * 更新认证状态
	 * @param companyId
	 * @param certifyStatus
	 * @param certifyResult
	 */
	public void updateCertifyStatus(Long companyId, Integer certifyStatus, String certifyResult){
		String hql = "update CmcLtCompany set certifystatus=:certifystatus, certifyresult=:certifyresult where companyid=:companyid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyid", companyId);
		params.put("certifystatus", certifyStatus);
		params.put("certifyresult", certifyResult);

		updateBatch(hql, params);
	}
}
