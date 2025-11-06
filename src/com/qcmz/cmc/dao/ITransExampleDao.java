package com.qcmz.cmc.dao;

import com.qcmz.cmc.entity.CmcTransExample;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：翻译例句
 * 修改历史：
 */
public interface ITransExampleDao extends IBaseDAO {
	/**
	 * 获取例句信息
	 * @param from
	 * @param to
	 * @param transSrc
	 * @return
	 * 修改历史：
	 */
	public CmcTransExample getBean(String from, String to, String transSrc, String src);
}
