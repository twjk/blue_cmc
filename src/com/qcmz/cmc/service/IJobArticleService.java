package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.framework.page.PageBean;

public interface IJobArticleService {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcJobArticle>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取有效的就业信息
	 * @param title
	 * @param catId
	 * @param cityId
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	public List<CmcJobArticle> findArticle(String title, Long catId, Long cityId, int pageSize, String moreBaseId);
	/**
	 * 获取文章
	 * @param articleId
	 * @return
	 */
	public CmcJobArticle getArticle(Long articleId);
	/**
	 * 获取文章，带来源、图片
	 * @param articleId
	 * @return
	 */
	public CmcJobArticle getArticleJoin(Long articleId);
	/**
	 * 保存
	 * @param bean
	 */
	public void saveOrUpdate(CmcJobArticle bean);
	/**
	 * 更新状态
	 * @param articleId
	 * @param status
	 */
	public void updateStatus(Long articleId, Integer status);
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id);
}
