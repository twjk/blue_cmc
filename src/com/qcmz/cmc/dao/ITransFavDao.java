package com.qcmz.cmc.dao;

import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITransFavDao extends IBaseDAO {
	/**
	 * 分页查询翻译收藏列表
	 * @param uid
	 * @param to
	 * @param pageBean
	 * 修改历史：
	 */
	public void findInfo(String uid, String to, PageBean pageBean);
}
