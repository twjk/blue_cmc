package com.qcmz.cmc.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IDaySentenceDao;
import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class DaySentenceDao extends BaseDAO implements IDaySentenceDao {
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcPkgDaysentence t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.sentid) from CmcPkgDaysentence t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//查询条件
			StringBuilder sbCond = new StringBuilder();
			
			//分类
			String lang = map.get("lang");
			if(StringUtil.notBlankAndNull(lang)){
				sbCond.append(" and (t.fromlang=:lang or t.tolang=:lang)");
				params.put("lang", lang);
			}

			//内容
			String content = map.get("content");
			if(StringUtil.notBlankAndNull(content)){
				sbCond.append(" and (t.src like :content or t.dst like :content)");
				params.put("content", "%"+content+"%");
			}
			
			//标题
			String title = map.get("title");
			if(StringUtil.notBlankAndNull(title)){
				sbCond.append(" and t.title like :title");
				params.put("title", "%"+title+"%");
			}
			
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:st");
				params.put("st", Integer.valueOf(status));
			}
			
			//提交时间
			String start = map.get("start");
			if(StringUtil.notBlankAndNull(start)){
				sbCond.append(" and t.releasetime>=:start");
				params.put("start", DateUtil.parseDate(start));
			}
			String end = map.get("end");
			if(StringUtil.notBlankAndNull(end)){
				sbCond.append(" and t.releasetime<=:end");
				params.put("end", DateUtil.setMaxTime(DateUtil.parseDate(end)));
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.releasetime desc, t.sentid desc");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取所有句子
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPkgDaysentence> findSentence(){
		return (List<CmcPkgDaysentence>) qryList("from CmcPkgDaysentence order by releasetime desc, sentid desc");
	}
	
	/**
	 * 获得待推送的句子
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPkgDaysentence> findSentence4Push(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcPkgDaysentence t ")
				 .append(" where t.status=1 and t.pushstatus=0")
				 .append(" and date_format(t.releasetime,'%Y-%m-%d')=:date")
		;
		params.put("date", DateUtil.formatDate(new Date()));
		
		return (List<CmcPkgDaysentence>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取每日一句列表
	 * @param from not null
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPkgDaysentence> findSentence(String from, Date start, Date end){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcPkgDaysentence")
			 .append(" where status=1 and fromlang=:fromlang")
			 .append(" and releasetime between :start and :end")
			 .append(" order by releasetime")				 
		;
		params.put("fromlang", from);
		params.put("start", DateUtil.setMinTime(start));
		params.put("end", DateUtil.setMaxTime(end));
		
		return (List<CmcPkgDaysentence>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取某一天的每日一句列表
	 * @param from
	 * @param day MMdd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcPkgDaysentence> findSentence(String from, String day){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcPkgDaysentence")
			 .append(" where status=1 and fromlang=:fromlang")
			 .append(" and date_format(releasetime,'%m%d')=:day")
			 .append(" order by releasetime desc")				 
		;
		params.put("fromlang", from);
		params.put("day", day);
		
		return (List<CmcPkgDaysentence>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 分页获取有效的每日一句
	 * @param from
	 * @param pageBean<CmcPkgDaysentence>
	 * 修改历史：
	 */
	public void findSentence(String from, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder();
		StringBuilder sbCountHql = new StringBuilder("select count(t.sentid)");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbCond.append(" from CmcPkgDaysentence t where t.status=1 and t.releasetime<now()");
		if(StringUtil.notBlankAndNull(from)){
			sbCond.append(" and fromlang=:fromlang");
			params.put("fromlang", from);
		}
		
		sbHql.append(sbCond).append(" order by t.releasetime desc, t.sentid desc");
		sbCountHql.append(sbCond);
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取有效的每日一句列表
	 * @param maxResults
	 * @param maxTime
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<DaySentenceBean> findSentenceInfo(final int maxResults, final Date maxTime){
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder sbHql = new StringBuilder()
			.append("select new com.qcmz.cmc.ws.provide.vo.DaySentenceBean(")
			.append("sentid, t.releasetime, t.title, t.fromlang, t.src, t.tolang, t.dst, t.source, t.pic, t.htmlurl")
			.append(") from CmcPkgDaysentence t where t.status=1 and t.releasetime<=:maxTime")
			.append(" order by t.releasetime desc, t.sentid desc")
		;
		params.put("maxTime", maxTime);		
		
		return (List<DaySentenceBean>) getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(sbHql.toString()).setProperties(params).setMaxResults(maxResults).list();
			}
		});
	}
	
	/**
	 * 分页获取每日一句编号
	 * @param pageBean
	 * 修改历史：
	 */
	public void findId(PageBean pageBean){
		String hql = "select sentid from CmcPkgDaysentence order by sentid";
		String countHql = "select count(sentid) from CmcPkgDaysentence";

		getQueryPageBean(hql, countHql, pageBean);
	}
	
	/**
	 * 获取有效的句子信息
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getSentenceInfo(Long sentId){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.DaySentenceBean(")
		 	 .append("sentid, t.releasetime, t.title, t.fromlang, t.src, t.tolang, t.dst, t.source, t.pic, t.htmlurl")
		 	 .append(")")
		 	 .append(" from CmcPkgDaysentence t where t.status=1 and t.releasetime<now()")
		 	 .append(" and t.sentid=?")
		;
		return (DaySentenceBean) load(sbHql.toString(), sentId);
	}
	
	/**
	 * 更新状态
	 * @param sentId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(Long sentId, Integer status){
		update("update CmcPkgDaysentence t set t.status=? where t.sentid=?", new Object[]{status, sentId});
	}
	
	/**
	 * 更新图片
	 * @param sentId
	 * @param picUrl
	 */
	public void updatePic(Long sentId, String picUrl){
		update("update CmcPkgDaysentence set pic=? where sentid=?", new Object[]{picUrl, sentId});
	}
	
	/**
	 * 更新小图
	 * @param sentId
	 * @param picUrl
	 */
	public void updateSmallPic(Long sentId, String picUrl){
		update("update CmcPkgDaysentence set smallpic=? where sentid=?", new Object[]{picUrl, sentId});
	}
	
	/**
	 * 更新静态页地址
	 * @param sentId
	 * @param htmlUrl
	 * 修改历史：
	 */
	public void updateHtmlUrl(Long sentId, String htmlUrl){
		update("update CmcPkgDaysentence t set t.htmlurl=? where t.sentid=?", new Object[]{htmlUrl, sentId});
	}
	
	/**
	 * 查重
	 * 源语言+发布日期不能重复
	 * @param sentId
	 * @param releaseTime
	 * 修改历史：
	 */
	public boolean isExist(Long sentId, Date releaseTime, String from){
		if(releaseTime==null){
			return false;
		}
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.sentid) from CmcPkgDaysentence t")
				 .append(" where date_format(t.releasetime,'%Y-%m-%d')=:rt")
				 .append(" and fromlang=:fromlang")
		;
		params.put("rt", DateUtil.formatDate(releaseTime));
		params.put("fromlang", from);
		
		if(sentId!=null){
			sbHql.append(" and t.sentid!=:sentid");
			params.put("sentid", sentId);
		}
		
		Long count = (Long) load(sbHql.toString(), params);
		return count>0;
	}
}