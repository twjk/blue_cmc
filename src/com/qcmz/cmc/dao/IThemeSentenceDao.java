package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPkgSentence;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public interface IThemeSentenceDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取主题语句信息
	 * @param themeId
	 * @param catId
	 * @author 李炳煜
	 * 修改历史：
	 */
	public List<ThemeSentenceBean> findSentenceInfo(Long themeId, String catId);
	/**
	 * 获取明细信息，含主题信息
	 * @param detailId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgSentence getJoinTheme(Long detailId);
	/**
	 * 获取指定主题一级分类列表
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public List<String> findCatLev1(Long themeId);
	/**
	 * 查重
	 * @param bean
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public boolean isExist(CmcPkgSentence bean);
	/**
	 * 清除主题语句
	 * @param themeId
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void clearSentence(Long themeId);
}
