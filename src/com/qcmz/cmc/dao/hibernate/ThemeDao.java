package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IThemeDao;
import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
@Repository
public class ThemeDao extends BaseDAO implements IThemeDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcPkgTheme t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.themeid) from CmcPkgTheme t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			
			//名称
			String title = (String) map.get("title");
			if(StringUtil.notBlankAndNull(title)){
				sbCond.append(" and (t.titlecn like :title or t.titlelc like :title)");
				params.put("title", "%"+title+"%");
			}
			
			//状态
			String status = (String) map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.sortindex, t.themeid");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取有效的主题列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPkgTheme> findValidTheme(){
		return (List<CmcPkgTheme>) qryList("from CmcPkgTheme t where t.status=1");
	}
	
	/**
	 * 分页获取有效的主题列表
	 * @param pageBean
	 * 修改历史：
	 */
	public void findThemeInfo(PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("select new com.qcmz.cmc.ws.provide.vo.ThemeBean(")
			.append("t.themeid, t.themecode, t.titlecn, t.titlelc, t.pic, t.downloadurl, t.filesize")
			.append(")from CmcPkgTheme t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.themeid) from CmcPkgTheme t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder sbCond = new StringBuilder();
		
		sbCond.append(" where t.status=1");
		
		sbHql.append(sbCond);
		sbCountHql.append(sbCond);
		
		sbHql.append(" order by t.sortindex, t.themeid");
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取主题信息
	 * @param themeCode
	 * @return
	 * 修改历史：
	 */
	public CmcPkgTheme getThemeByCode(String themeCode){
		return (CmcPkgTheme) this.load("from CmcPkgTheme t where t.themecode=?", themeCode);
	}
	
	/**
	 * 校验是否存在
	 * @param themeId
	 * @param title
	 * @return
	 * 修改历史：
	 */
	public boolean isExist(Long themeId, String title){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.themeid) from CmcPkgTheme t where t.titlecn=:title");
		params.put("title", title);
		
		if(themeId!=null){
			sbHql.append(" and t.themeid!=:themeid");
			params.put("themeid", themeId);
		}
		
		Long count = (Long) load(sbHql.toString(), params);
		return count>0;
	}
}
