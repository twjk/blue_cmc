package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransFavDao;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class TransFavDao extends BaseDAO implements ITransFavDao {
	
	/**
	 * 分页查询翻译收藏列表
	 * @param uid
	 * @param to
	 * @param pageBean
	 * 修改历史：
	 */
	public void findInfo(String uid, String to, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("select new com.qcmz.cmc.ws.provide.vo.TransFavBean(t.fromlang, t.src, t.tolang, t.dst) from CmcTransFav t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.favid) from CmcTransFav t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder sbCond = new StringBuilder();
		sbCond.append(" where t.uid=:uid");
		params.put("uid", uid);
		
		if(StringUtil.notBlankAndNull(to)){
			sbCond.append(" and t.tolang=:tolang");
			params.put("tolang", to);
		}
		
		sbHql.append(sbCond);
		sbCountHql.append(sbCond);
		
		if(SystemConstants.OPERATOR_DEFAULT.equals(uid)){
			sbHql.append(" order by t.favid");
		}else{
			sbHql.append(" order by t.favid desc");
		}
		
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
}
