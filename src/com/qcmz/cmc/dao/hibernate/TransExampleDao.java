package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransExampleDao;
import com.qcmz.cmc.entity.CmcTransExample;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class TransExampleDao extends BaseDAO implements ITransExampleDao {
	/**
	 * 获取例句信息
	 * @param from
	 * @param to
	 * @param transSrc
	 * @return
	 * 修改历史：
	 */
	public CmcTransExample getBean(String from, String to, String transSrc, String src){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transsrc", transSrc);
		params.put("fromlang", from);
		params.put("tolang", to);
		params.put("src", src);
		
		sbHql.append("from CmcTransExample t")
			 .append(" where t.transsrc=:transsrc")
			 .append(" and t.fromlang=:fromlang")
			 .append(" and t.tolang=:tolang")
			 .append(" and t.src=:src")
		;
		
		return (CmcTransExample) load(sbHql.toString(), params);
	}
}
