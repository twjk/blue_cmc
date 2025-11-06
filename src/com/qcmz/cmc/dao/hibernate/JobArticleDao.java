package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IJobArticleDao;
import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class JobArticleDao extends BaseDAO implements IJobArticleDao {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcJobArticle>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcJobArticle t left join fetch t.cmcLtSource");
		StringBuilder sbCountHql = new StringBuilder("select count(t.artid) from CmcJobArticle t left join t.cmcLtSource");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//根据Map参数添加查询条件
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			String param = null;
			
			param = map.get("title");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and upper(t.title) like :title");
				params.put("title", "%"+param.toUpperCase()+"%");
			}
			param = map.get("cat");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and t.fullcatid like :fullcatid");
				params.put("fullcatid", param+"%");
			}
			param = map.get("sourcename");
			if(StringUtil.notBlankAndNull(param)){
				sbCond.append(" and  t.cmcLtSource.sourcename like :sourcename");
				params.put("sourcename", "%"+param+"%");
			}
			param = map.get("cityid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.cityid=:cityid");
				params.put("cityid", Long.valueOf(param));	
			}
			param = map.get("status");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(param));	
			}
			param = map.get("isact");
			if(Boolean.valueOf(param)){
				sbCond.append(" and t.actid is not null");
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by t.sortquery");
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取有效的就业信息
	 * @param title
	 * @param catId
	 * @param cityId
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcJobArticle> findArticle(String title, Long catId, Long cityId, int pageSize, String moreBaseId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcJobArticle t where t.status=1 ");
		
		if(catId!=null){
			sbHql.append(" and t.catid = :catid");
			params.put("catid", catId);
		}
		
		if(cityId!=null){
			sbHql.append(" and t.cityid = :cityid");
			params.put("cityid", cityId);
		}
		
		if(StringUtil.notBlankAndNull(title)){
			sbHql.append(" and t.title like :title");
			params.put("title", "%"+title.trim()+"%");			
		}
		
		//分页
		if(NumberUtil.isNumber(moreBaseId)){
			sbHql.append(" and t.sortquery>:baseid");
			params.put("baseid", moreBaseId);
		}
		
		//排序
		sbHql.append(" order by t.sortquery");
		
		//查询
		return (List<CmcJobArticle>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取文章，带来源
	 * @param articleId
	 * @return
	 */
	public CmcJobArticle getArticleJoin(Long articleId){
		String hql = "from CmcJobArticle t left join fetch t.cmcLtSource where t.artid=:artid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artid", articleId);
		return (CmcJobArticle) load(hql, params);
	}
	
	/**
	 * 更新状态
	 * @param articleId
	 * @param status
	 */
	public void updateStatus(Long articleId, Integer status){
		String hql = "update CmcJobArticle set status=:status where artid=:artid";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artid", articleId);
		params.put("status", status);

		updateBatch(hql, params);
	}
}