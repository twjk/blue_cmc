package com.qcmz.cmc.service;

/**
 * 类说明：翻译相似度
 * 修改历史：
 */
public interface ITransSimilarService {
	/**
	 * 保存相似度计数
	 * @param from
	 * @param to
	 * @param proxyId
	 * @param similar
	 * @param count
	 * 修改历史：
	 */
	public void save(String from, String to, Long proxyId, Integer similar, Integer count);
}
