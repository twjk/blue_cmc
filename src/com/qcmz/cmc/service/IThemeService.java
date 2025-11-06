package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.cmc.ws.provide.vo.ThemeDetailBean;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public interface IThemeService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean);
	/***
	 * 分页获取有效的主题列表
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void findThemeInfo(PageBean pageBean);
	/**
	 * 获取主题信息
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgTheme load(Long themeId);
	/**
	 * 获取主题详细信息
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeDetailBean getDetailInfo(Long themeId);
	/**
	 * 获取主题下载信息
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeDownloadBean getDownloadInfo(Long themeId);
	/**
	 * 获取主题下载信息
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeDownloadBean getDownloadInfo(String themeCode);
	/**
	 * 获取主题下载信息
	 * @param themeId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeDownloadBean getDownloadInfo(Long themeId, String themeCode);
	/**
	 * 保存或修改
	 * @param bean
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgTheme saveOrUpdate(CmcPkgTheme bean, Operator oper);
	/**
	 * 删除主题
	 * @param themeId
	 * @param oper
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgTheme delete(Long themeId, Operator oper);
}
