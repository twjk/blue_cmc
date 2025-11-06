package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITmDao;
import com.qcmz.cmc.entity.CmcTm;
import com.qcmz.cmc.entity.CmcTmVersion;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

@Repository
public class TmDao extends BaseDAO implements ITmDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTm>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcTm t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.tmid) from CmcTm t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			//名称
			String tmcode = (String) map.get("tmcode");
			if(StringUtil.notBlankAndNull(tmcode)){
				sbCond.append(" and t.tmcode=:tmcode");
				params.put("tmcode", tmcode);
			}
		
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.tmid desc");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取所有翻译机信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTm> findTm(){
		return (List<CmcTm>) qryList("from CmcTm");
	}
	
	/**
	 * 获取最新版本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmcTmVersion getLastVersion(){
		String hql = "from CmcTmVersion order by versionid desc";
		List<CmcTmVersion> list = (List<CmcTmVersion>) qryListTop(hql, new HashMap<String, Object>(), 1);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取翻译机信息
	 * @param tmCode
	 * @return
	 */
	public CmcTm getTm(String tmCode){
		return (CmcTm) load("from CmcTm where tmcode=?", tmCode);
	}
}
