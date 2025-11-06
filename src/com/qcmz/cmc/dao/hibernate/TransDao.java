package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransDao;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Aug 29, 2014 3:49:38 PM
 * 修改历史：
 */
@Repository
public class TransDao extends BaseDAO implements ITransDao {
	/**
	 * 查询某用户当天翻译次数
	 * @param token 用户标识
	 * @param excLang 不参加计数的语言
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Sep 4, 2014 3:46:29 PM
	 * 修改历史：
	 */
	public Long getTransCountToday(String token, List<String> excLang){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.logid) from CmcTransLog t")
			 .append(" where t.token=:token")
			 .append(" and t.transtime>:transtime");
		;
		params.put("token", token);
		params.put("transtime", DateUtil.truncDate(new Date()));		
		
		if(excLang!=null && !excLang.isEmpty()){
			sbHql.append(" and t.srclang not in (:srclang)");
			params.put("srclang", excLang);
		}
		
		return (Long) load(sbHql.toString(), params);
	}
}
