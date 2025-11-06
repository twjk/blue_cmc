package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.cmc.ws.provide.vo.TransUserCorrectBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface ITransUserCorrectService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTransUsercorrect>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 保存用户纠错记录
	 * @param bean
	 */
	public void saveUserCorrect(TransUserCorrectBean bean);
	/**
	 * 处理用户纠错记录
	 * @param correctId
	 * @param dst
	 * @param operator
	 * @return
	 */
	public CmcTransCorrect dealUserCorrect(Long correctId, String dst, Operator operator);
	/**
	 * 删除用户纠错记录
	 * @param correctId
	 */
	public void deleteUserCorrect(Long correctId, Operator operator);
}
