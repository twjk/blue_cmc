package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.framework.page.PageBean;

public interface ITransCorrectService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取修正翻译列表
	 * @param uuid
	 * @param minTime
	 * @return
	 */
	public List<CmcTransCorrect> findCorrectTrans(String uuid, Date minTime);
}
