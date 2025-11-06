package com.qcmz.cmc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.dao.IJobArticleDao;
import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.service.IJobArticlePicService;
import com.qcmz.cmc.service.IJobArticleService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

@Service
public class JobArticleServiceImpl implements IJobArticleService {
	@Autowired
	private IJobArticleDao jobArticleDao;
	@Autowired
	private IJobArticlePicService jobArticlePicService;
	@Autowired
	private RewardActivityMap rewardActivityMap;
	
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcJobArticle>
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		jobArticleDao.queryByMapTerm(map, pageBean);
		List<CmcJobArticle> list = (List<CmcJobArticle>) pageBean.getResultList();
		for (CmcJobArticle entity : list) {
			if(entity.getActid()!=null){
				entity.setActivity(rewardActivityMap.getActivity(entity.getActid()));
			}
		}
	}

	/**
	 * 分页获取有效的就业信息
	 * @param title
	 * @param catId
	 * @param cityId
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	public List<CmcJobArticle> findArticle(String title, Long catId, Long cityId, int pageSize, String moreBaseId){
		return jobArticleDao.findArticle(title, catId, cityId, pageSize, moreBaseId);
	}

	/**
	 * 获取文章
	 * @param articleId
	 * @return
	 */
	public CmcJobArticle getArticle(Long articleId){
		return (CmcJobArticle) jobArticleDao.load(CmcJobArticle.class, articleId);
	}
	
	/**
	 * 获取文章，带来源、图片
	 * @param articleId
	 * @return
	 */
	public CmcJobArticle getArticleJoin(Long articleId){
		CmcJobArticle article = jobArticleDao.getArticleJoin(articleId);
		
		article.setPics(jobArticlePicService.findPic(articleId));
		if(article.getActid()!=null){
			article.setActivity(rewardActivityMap.getActivity(article.getActid()));
		}
		return article;
	}
	
	/**
	 * 保存
	 * @param bean
	 */
	public void saveOrUpdate(CmcJobArticle bean){
		boolean update = false;
		
		Long catId = null;
		if(StringUtil.notBlankAndNull(bean.getFullcatid())){
			catId = Long.valueOf(StringUtil.right(bean.getFullcatid(), 4));
		}
		
		if(bean.getArtid()==null){
			bean.setCatid(catId);
			jobArticleDao.save(bean);
			
			bean.setSortquery(CmcUtil.getSortQuery(bean.getSortindex(), bean.getArtid(), bean.getPublishtime()));
			jobArticleDao.update(bean);
		}else{
			update = true;
			CmcJobArticle entity = getArticle(bean.getArtid());
			entity.setTitle(bean.getTitle());
			entity.setFullcatid(bean.getFullcatid());
			entity.setCatid(catId);
			entity.setSortindex(bean.getSortindex());
			entity.setSortquery(CmcUtil.getSortQuery(entity.getSortindex(), entity.getArtid(), entity.getPublishtime()));
			entity.setStatus(bean.getStatus());
			entity.setActid(bean.getActid());
			
			jobArticleDao.update(entity);
		}
		
		//图片
		if(!update){
			jobArticlePicService.savePic(bean.getArtid(), bean.getPics(), update);
		}
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id){
		jobArticlePicService.clearPic(id);
		jobArticleDao.delete(CmcJobArticle.class, id);
	}
	
	/**
	 * 更新状态
	 * @param articleId
	 * @param status
	 */
	public void updateStatus(Long articleId, Integer status){
		jobArticleDao.updateStatus(articleId, status);
	}
}
