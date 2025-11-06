package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：每日一句
 * 修改历史：
 */
public interface IDaySentenceService {
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
	 * 获取单条信息
	 * @return
	 * 修改历史：
	 */
	public CmcPkgDaysentence load(Long id);
	/**
	 * 获取有效的句子信息
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getSentenceInfo(Long sentId);
	/**
	 * 获取下一条句子
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getNextSentenceInfo(Long sentId, String from);
	/**
	 * 获取上一条句子
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getPreSentenceInfo(Long sentId, String from);
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
	 * 分页获取每日一句编号
	 * @param pageBean
	 * 修改历史：
	 */
	public void findId(PageBean pageBean);
	/**
	 * 保存
	 * @param bean
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public CmcPkgDaysentence saveOrUpdate(CmcPkgDaysentence bean, Operator oper);
	/**
	 * 批量更新
	 * @param list
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<CmcPkgDaysentence> list);
	/**
	 * 更新状态
	 * @param sentId
	 * @param status
	 * @param oper
	 * 修改历史：
	 */
	public void updateStatus(Long sentId, Integer status, Operator oper);
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
	 * 删除
	 * @param id
	 * @param oper
	 * 修改历史：
	 */
	public void delete(Long id, Operator oper);
	/**
	 * 生成静态页面
	 * @param sentId
	 * @return 静态页地址
	 * 修改历史：
	 */
	public String createHtml(Long sentId);
}
