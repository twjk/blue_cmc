package com.qcmz.cmc.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransLibDao;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.TransLibrary4ImportBean;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：译库维护
 * 修改历史：
 */
@Repository
public class TransLibDao extends BaseDAO implements ITransLibDao {
	/**
	 * 分页获取译库列表
	 * @param map
	 * @param pageBean<TransLibraryEntity>
	 * 修改历史：
	 */
	public void queryByMapTerm(String libClass, Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder(getTransLibraryEntityHql(libClass));
		StringBuilder sbCountHql = new StringBuilder("select count(t.libid) from ").append(libClass).append(" t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			//源语言
			String fromlang = (String) map.get("fromlang");
			if(StringUtil.notBlankAndNull(fromlang)){
				sbCond.append(" and t.fromlang=:fromlang");
				params.put("fromlang", fromlang);
			}
			//目标语言
			String tolang = (String) map.get("tolang");
			if(StringUtil.notBlankAndNull(tolang)){
				sbCond.append(" and t.tolang=:tolang");
				params.put("tolang", tolang);
			}
			//原文
			String src = (String) map.get("src");
			if(StringUtil.notBlankAndNull(src)){
				String srcmatch = (String)map.get("srcmatch");
				if("1".equals(srcmatch)){
					sbCond.append(" and lower(t.src) = :src");
					params.put("src", src.toLowerCase());
				}else{
					sbCond.append(" and lower(t.src) like :src");
					params.put("src", "%"+src.toLowerCase()+"%");
				}
			}
			//类型
			String libtype = (String) map.get("libtype");
			if(StringUtil.notBlankAndNull(libtype)){
				sbCond.append(" and t.libtype=:libtype");
				params.put("libtype", libtype);
			}
			//状态
			String ttssrc = (String) map.get("ttssrc");
			if(NumberUtil.isNumber(ttssrc)){
				sbCond.append(" and t.ttssrc=:ttssrc");
				params.put("ttssrc", Integer.valueOf(ttssrc));
			}
			//状态
			String status = (String) map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}
			//核对状态
			String checkstatus = (String) map.get("checkstatus");
			if(NumberUtil.isNumber(checkstatus)){
				sbCond.append(" and t.checkstatus=:checkstatus");
				params.put("checkstatus", Integer.valueOf(checkstatus));
			}
			//来源
			String sourceid = (String) map.get("sourceid");
			if(StringUtil.notBlankAndNull(sourceid)){
				sbCond.append(" and t.sourceid=:sourceid");
				params.put("sourceid", sourceid);
			}
			//分类
			String cat = (String) map.get("cat");
			if(StringUtil.notBlankAndNull(cat)){
				sbCond.append(" and t.cat like :cat");
				params.put("cat", cat+"%");
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			//排序
			String orderby = map.get("orderby");
			if(StringUtil.notBlankAndNull(orderby)){
				sbHql.append(" order by ").append(orderby);
			}else{
				sbHql.append(" order by t.fromlang, convert_gbk(t.src)");
			}
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}

	/**
	 * 分页获取译库列表
	 * @param libClass not null
	 * @param status
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransLibraryEntity> findLib(String libClass, Integer status, Long lastId, int pageSize){
		StringBuilder sbHql = new StringBuilder();
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append(getTransLibraryEntityHql(libClass));
		
		if(status!=null){
			sbCond.append(" and status=:status");
			params.put("status", status);
		}
		if(lastId!=null){
			sbCond.append(" and libid>:libid");
			params.put("libid", lastId);
		}
		if(sbCond.length()>0){
			sbCond.replace(1, 4, "where");
			sbHql.append(sbCond);
		}
		
		sbHql.append(" order by libid");

		return (List<TransLibraryEntity>) qryListTop(sbHql.toString(), params, pageSize);
	}
	
	/**
	 * 获取译库列表
	 * @param libClass not null
	 * @param from not null
	 * @param to not null
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransLibraryEntity> findLib(String libClass, String from, String to){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append(getTransLibraryEntityHql(libClass)).append(" where t.fromlang=:from and t.tolang=:to");
		params.put("from", from);
		params.put("to", to);
		
		return (List<TransLibraryEntity>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取译库列表
	 * @param libClass not null
	 * @param from not null
	 * @param src not null
	 * @param to not null
	 * @param status
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransLibraryEntity> findLib(String libClass, String from, String src, String to, Integer status){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append(getTransLibraryEntityHql(libClass))
			 .append(" where t.fromlang=:from and t.src=:src")
			 .append(" and t.tolang=:to")
			 ;
		params.put("from", from);
		params.put("src", src);
		params.put("to", to);
		
		if(status!=null){
			sbHql.append(" and t.status=:status");
			params.put("status", status);
		}
		
		return (List<TransLibraryEntity>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 查询译库
	 * @param ids
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransLibraryEntity> findLib(List<Long> ids){
		List<TransLibraryEntity> result = new ArrayList<TransLibraryEntity>();
		List<TransLibraryEntity> libs = null;
		
		StringBuilder sbHql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		
		
		List<String> libClasses = TransUtil.findAllLibClassWithoutTemp();
		for (String libClass : libClasses) {
			sbHql = new StringBuilder().append(getTransLibraryEntityHql(libClass)).append(" where t.libid in(:ids)");
			libs = (List<TransLibraryEntity>) qryListByMap(sbHql.toString(), params);
			if(!libs.isEmpty()){
				result.addAll(libs);
			}
		}
		return result;
	}
	
	/**
	 * 获取译库列表
	 * @param libClass
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransLibraryEntity> findLib4Cache(String libClass){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append(getTransLibraryEntityHql(libClass))
			 .append(" where t.status=:status");
		
		params.put("status", SystemConstants.STATUS_ON);

		return (List<TransLibraryEntity>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取待导入的译库
	 * @param libClass not null
	 * @param themeId not null
	 * @param from not null
	 * @param to not null
	 * @param key
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<TransLibrary4ImportBean> findLib4ImportTheme(String libClass, Long themeId, String libType, String from, String to, String key){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select new com.qcmz.cmc.vo.TransLibrary4ImportBean(")
				.append("t.libid, t.fromlang, t.src, t.tolang, t.dst, t.cat")
				.append(") from ").append(libClass).append(" t")
			// .append(" where (t.voice!=null or t.voice!='')")
				.append(" where t.status=:status")
				.append(" and not exists(from CmcPkgSentence d where d.libid=t.libid and d.cmcPkgTheme.themeid=:themeid )")
				.append(" and t.libtype=:libtype")
				.append(" and t.fromlang=:fromlang and t.tolang=:tolang")				
			 ;		
		params.put("status", SystemConstants.STATUS_ON);
		params.put("themeid", themeId);
		params.put("libtype", libType);
		params.put("fromlang", from);
		params.put("tolang", to);
		
		if(StringUtil.notBlankAndNull(key)){
			sbHql.append(" and t.src like (:key)");
			params.put("key", "%"+key+"%");
		}
		
		sbHql.append(" order by t.tolang, t.cat, convert_gbk(t.src)");
		
		return (List<TransLibrary4ImportBean>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 获取英文原文
	 * @param limit
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findEnSrc(int limit){
		return (List<String>) qryListTop("select src from CmcTransLibraryEn2cn", new HashMap<String, Object>(), limit);
	}
	
	/**
	 * 获取译库源语言列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findFromLang(){
		List<String> result = new ArrayList<String>();
		List<String> langs = null;
		Set<String> set = new HashSet<String>();
		
		StringBuilder sbHql = null;
		
		List<String> libClasses = TransUtil.findAllLibClassWithoutTemp();
		for (String libClass : libClasses) {
			sbHql = new StringBuilder().append("select distinct fromlang from ").append(libClass);
			langs = (List<String>) qryList(sbHql.toString());
			if(!langs.isEmpty()){
				set.addAll(langs);
			}
		}
		
		result.addAll(set);
		
		return result;
	}
	
	/**
	 * 获取译库目标语言列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findToLang(){
		List<String> result = new ArrayList<String>();
		List<String> langs = null;
		Set<String> set = new HashSet<String>();
		
		StringBuilder sbHql = null;
		
		List<String> libClasses = TransUtil.findAllLibClassWithoutTemp();
		for (String libClass : libClasses) {
			sbHql = new StringBuilder().append("select distinct tolang from ").append(libClass);
			langs = (List<String>) qryList(sbHql.toString());
			if(!langs.isEmpty()){
				set.addAll(langs);
			}
		}
		
		result.addAll(set);
		
		return result;
	}
	
	/**
	 * 获取单条译库信息
	 * @param libClass
	 * @param libId
	 * @return
	 * 修改历史：
	 */
	public TransLibraryEntity getLib(String libClass, Long libId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append(getTransLibraryEntityHql(libClass)).append(" where t.libid=:libid");
		params.put("libid", libId);
		
		return (TransLibraryEntity) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取单条译库信息
	 * @param libClass not null
	 * @param from not null
	 * @param src not null
	 * @param to not null
	 * @param dst not null
	 * @return
	 * 修改历史：
	 */
	public TransLibraryEntity getLib(String libClass, String from, String src, String to, String dst){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append(getTransLibraryEntityHql(libClass))
			 .append(" where t.fromlang=:from and t.src=:src")
			 .append(" and t.tolang=:to and t.dst=:dst")
			 ;
		
		params.put("from", from);
		params.put("src", src);
		params.put("to", to);
		params.put("dst", dst);		
		
		return (TransLibraryEntity) load(sbHql.toString(), params);
	}
	
	/**
	 * 获取封装TransLibraryEntity的hql语句
	 * "select new ...TransLibraryEntity(...) from xxx t" 
	 * @return
	 * 修改历史：
	 */
	private String getTransLibraryEntityHql(String libClass){
		String catName = "''";
		if(TransUtil.LIBCLASS_TEMP.equals(libClass)){
			catName = "t.catname";
		}
		return new StringBuilder()
			.append("select new com.qcmz.cmc.vo.TransLibraryEntity(")
			.append("'").append(libClass).append("'")
			.append(", t.libid, t.libtype, t.fromlang, t.src, t.tolang, t.dst, t.twoway, t.cat")
			.append(",").append(catName)
			.append(", t.voice, t.ttssrc, t.ttstext, t.remark")
			.append(", t.hitcount, t.similar, t.status, t.checkstatus, t.sourceid")
			.append(") from ").append(libClass).append(" t")
			.toString();
	}
	
	/**
	 * 判断是否重复
	 * @param libClass
	 * @param from
	 * @param src
	 * @param to
	 * @param libId
	 * @return
	 * 修改历史：
	 */
	public boolean isRepeat(String libClass, String from, String src, String to, String dst, Long libId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.libid) from ")
			 .append(libClass)
			 .append(" t where t.fromlang=:from and t.src=:src ")
			 .append(" and t.tolang=:to and t.dst=:dst")
			 ;
		params.put("from", from);
		params.put("src", src);
		params.put("to", to);
		params.put("dst", dst);
		
		if(libId!=null){
			sbHql.append(" and t.libid!=:libid");
			params.put("libid", libId);
		}
		
		Long count = (Long)load(sbHql.toString(), params);
		
		return count>0;
	}
	
	/**
	 * 判断是否存在
	 * @param libClass
	 * @param from
	 * @param src
	 * @param to
	 * @return
	 * 修改历史：
	 */
	public boolean isExist(String libClass, String from, String src, String to){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.libid) from ").append(libClass).append(" t")
			 .append(" where t.fromlang=:from and t.src=:src")
			 .append(" and t.tolang=:to")
			 ;
		
		params.put("from", from);
		params.put("src", src);
		params.put("to", to);
		
		Long count = (Long)load(sbHql.toString(), params);
		return count>0;
	}

	/**
	 * 获取新的译库编号
	 * @return
	 * 修改历史：
	 */
	public Long getNewLibId(){
		BigInteger newid = (BigInteger) getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select nextval('SEQ_CMC_TRANSLIB_ID')").uniqueResult();
			}
		});
		return newid.longValue();
	}
	
	/**
	 * 更新状态
	 * @param libClass
	 * @param libId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String libClass, Long libId, Integer status){
		update("update "+libClass+" set status=? where libid=?", new Object[]{status, libId});
	}
	
	/**
	 * 更新核对状态
	 * @param libClass
	 * @param libId
	 * @param checkStatus
	 * 修改历史：
	 */
	public void updateCheckStatus(String libClass, Long libId, Integer checkStatus){
		String hql = null;
		if(SystemConstants.STATUS_ON.equals(checkStatus)){
			hql = "update "+libClass+" set checkstatus=?, similar=100 where libid=?";
		}else{
			hql = "update "+libClass+" set checkstatus=? where libid=?";
		}
		update(hql, new Object[]{checkStatus, libId});
	}
	
	/**
	 * 增加命中次数
	 * @param libId
	 * @param times
	 * 修改历史：
	 */
	public void increaseHitCount(String libClass, Long libId, int times){
		this.update("update "+libClass+" set hitcount=hitcount+? where libid=?", new Object[]{Long.valueOf(times), libId});
	}
	
	/**
	 * 保存命中次数
	 * @param libClass
	 * @param libId
	 * @param hitCount
	 * 修改历史：
	 */
	public void updateHitCount(String libClass, Long libId, Long hitCount){
		this.update("update "+libClass+" set hitcount=? where libid=?", new Object[]{hitCount, libId});
	}
	
	/**
	 * 删除
	 * @param libClass
	 * @param libId
	 * 修改历史：
	 */
	public void deleteLib(String libClass, Long libId){
		delete("delete from "+libClass+" where libid=?", libId);
	}
}
