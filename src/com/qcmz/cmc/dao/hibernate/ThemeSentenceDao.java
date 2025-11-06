package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IThemeSentenceDao;
import com.qcmz.cmc.entity.CmcPkgSentence;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceBean;
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
public class ThemeSentenceDao extends BaseDAO implements IThemeSentenceDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcPkgSentence t left join fetch t.cmcPkgTheme");
		StringBuilder sbCountHql = new StringBuilder("select count(t.sentid) from CmcPkgSentence t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			//主题编号
			String themeid = map.get("themeid");
			if(NumberUtil.isNumber(themeid)){
				sbCond.append(" and t.cmcPkgTheme.themeid=:themeid");
				params.put("themeid", Long.valueOf(themeid));
			}
			
			//分类
			String cat = map.get("cat");
			if(StringUtil.notBlankAndNull(cat)){
				sbCond.append(" and t.cat=:cat");
				params.put("cat", cat);
			}
			
			//语言
			String tolang = map.get("tolang");
			if(StringUtil.notBlankAndNull(tolang)){
				sbCond.append(" and t.tolang=:tolang");
				params.put("tolang", tolang);
			}
			
			//内容
			String src = map.get("src");
			if(StringUtil.notBlankAndNull(src)){
				sbCond.append(" and t.src like :src");
				params.put("src", "%"+src+"%");
			}
			
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.cat, t.src, t.sentid");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取主题语句信息
	 * @param themeId
	 * @param catId
	 * @author 李炳煜
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<ThemeSentenceBean> findSentenceInfo(Long themeId, String catId){
		StringBuilder sbHql = new StringBuilder("select new com.qcmz.cmc.ws.provide.vo.ThemeSentenceBean(")
			.append("t.fromlang, t.src, t.tolang, t.dst, t.cat")
			.append(") from CmcPkgSentence t")
			.append(" where t.themeid=:themeid")
			;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("themeid", themeId);
		
		if(StringUtil.notBlankAndNull(catId)){
			sbHql.append(" and t.cat like :cat");
			params.put("cat", catId+"%");
		}
		
		sbHql.append(" order by t.cat, t.src, t.sentid");
		
		return (List<ThemeSentenceBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取语句，含主题信息
	 * @param sentid
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgSentence getJoinTheme(Long sentid){
		return (CmcPkgSentence) load("from CmcPkgSentence t left join fetch t.cmcPkgTheme where t.sentid=?", sentid);
	}
	
	/**
	 * 获取指定主题一级分类列表
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findCatLev1(Long themeId){
		return (List<String>) qryList("select distinct left(t.cat,4) from CmcPkgSentence t where t.themeid=?", themeId);
	}
	
	/**
	 * 查重
	 * @param bean
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public boolean isExist(CmcPkgSentence bean){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.sentid) from CmcPkgSentence t where t.cmcPkgTheme.themeid=:themeid")
			 .append(" and t.fromlang=:fromlang")
			 .append(" and t.tolang=:tolang")
			 .append(" and t.src=:src")
			 ;
		params.put("themeid", bean.getThemeid());
		params.put("fromlang", bean.getFromlang());
		params.put("tolang", bean.getTolang());
		params.put("src", bean.getSrc());
		
		if(bean.getSentid()!=null){
			sbHql.append(" and t.sentid!=:sentid");
			params.put("sentid", bean.getSentid());
		}
		if(StringUtil.notBlankAndNull(bean.getCat())){
			sbHql.append(" and t.cat=:cat");
			params.put("cat", bean.getCat());
		}
		
		Long count = (Long) load(sbHql.toString(), params);
		return count>0;
	}
	
	/**
	 * 清除主题语句
	 * @param themeId
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void clearSentence(Long themeId){
		this.delete("delete from CmcPkgSentence t where t.cmcPkgTheme.themeid=?", themeId);
	}
}

