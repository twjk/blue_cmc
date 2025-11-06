package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IAccountDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
}
