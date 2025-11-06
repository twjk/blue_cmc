package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：每日一句
 * 修改历史：
 */
public interface IDaySentenceDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取所有句子
	 * @return
	 */
	public List<CmcPkgDaysentence> findSentence();
	/**
	 * 获得待推送的句子
	 * @return
	 * 修改历史：
	 */
	public List<CmcPkgDaysentence> findSentence4Push();
	/**
	 * 获取每日一句列表
	 * @param from not null
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	public List<CmcPkgDaysentence> findSentence(String from, Date start, Date end);
	/**
	 * 获取某一天的每日一句列表
	 * @param from
	 * @param day MMdd
	 * @return
	 */
	public List<CmcPkgDaysentence> findSentence(String from, String day);
	/**
	 * 分页获取有效的每日一句
	 * @param from
	 * @param pageBean<CmcPkgDaysentence>
	 * 修改历史：
	 */
	public void findSentence(String from, PageBean pageBean);
	/**
	 * 获取有效的每日一句列表
	 * @param maxResults
	 * @param maxTime
	 * @return
	 * 修改历史：
	 */
	public List<DaySentenceBean> findSentenceInfo(int maxResults, Date maxTime);
	/**
	 * 分页获取每日一句编号
	 * @param pageBean
	 * 修改历史：
	 */
	public void findId(PageBean pageBean);
	/**
	 * 获取有效的句子信息
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getSentenceInfo(Long sentId);
	/**
	 * 更新状态
	 * @param sentId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(Long sentId, Integer status);
	/**
	 * 更新图片
	 * @param sentId
	 * @param picUrl
	 */
	public void updatePic(Long sentId, String picUrl);
	/**
	 * 更新小图
	 * @param sentId
	 * @param picUrl
	 */
	public void updateSmallPic(Long sentId, String picUrl);
	/**
	 * 更新静态页地址
	 * @param sentId
	 * @param htmlUrl
	 * 修改历史：
	 */
	public void updateHtmlUrl(Long sentId, String htmlUrl);
	/**
	 * 查重
	 * 源语言+发布日期不能重复
	 * @param sentId
	 * @param releaseTime
	 * 修改历史：
	 */
	public boolean isExist(Long sentId, Date releaseTime, String from);
}
