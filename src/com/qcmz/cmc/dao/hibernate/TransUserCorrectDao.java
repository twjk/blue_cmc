package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ITransUserCorrectDao;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class TransUserCorrectDao extends BaseDAO implements	ITransUserCorrectDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTransUsercorrect>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcTransUsercorrect t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.correctid) from CmcTransUsercorrect t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//查询条件
			StringBuilder sbCond = new StringBuilder();
			
			//源语言
			String fromlang = map.get("fromlang");
			if(StringUtil.notBlankAndNull(fromlang)){
				sbCond.append(" and(t.fromlang=:fromlang)");
				params.put("fromlang", fromlang);
			}
			
			//目标语言
			String tolang = map.get("tolang");
			if(StringUtil.notBlankAndNull(tolang)){
				sbCond.append(" and(t.tolang=:tolang)");
				params.put("tolang", tolang);
			}

			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:st");
				params.put("st", Integer.valueOf(status));
			}
			
			//pushregid
			String pushregid = map.get("pushregid");
			if(StringUtil.notBlankAndNull(pushregid)){
				sbCond.append(" and(t.pushregid=:pushregid)");
				params.put("pushregid", pushregid);
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.correctid desc");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
}