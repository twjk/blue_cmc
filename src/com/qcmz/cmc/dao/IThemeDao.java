package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public interface IThemeDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean);
	/**
	 * 获取有效的主题列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcPkgTheme> findValidTheme();
	/**
	 * 分页获取有效的主题列表
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void findThemeInfo(PageBean pageBean);
	/**
	 * 获取主题信息
	 * @param themeCode
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgTheme getThemeByCode(String themeCode);
	/**
	 * 校验是否存在
	 * @param themeId
	 * @param title
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public boolean isExist(Long themeId, String title);
}
