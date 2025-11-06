package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPkgSentence;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public interface IThemeSentenceService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取主题语句信息
	 * @param themeId
	 * @param catId
	 * 修改历史：
	 */
	public List<ThemeSentenceBean> findSentenceInfo(Long themeId, String catId);
	/**
	 * 获取明细信息
	 * @param detailId
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcPkgSentence load(Long detailId);
	/**
	 * 获取明细信息，含主题信息
	 * @param detailId
	 * @return
	 * 修改历史：
	 */
	public CmcPkgSentence getJoinTheme(Long detailId);
	/**
	 * 保存或修改
	 * @param bean
	 * @return
	 * 修改历史：
	 */
	public CmcPkgSentence saveOrUpdate(CmcPkgSentence bean, Operator oper);
	/**
	 * 从译库导入
	 * @param themeId
	 * @param cat
	 * @param arrLibid
	 * 修改历史：
	 */
	public void importFromLib(Long themeId, String cat, List<Long> libids, Operator oper);
	/**
	 * 删除
	 * @param detailId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public void delete(Long detailId, Operator oper);
}
