package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.DaySentenceReq;
import com.qcmz.cmc.ws.provide.vo.DaySentenceResp;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public interface IDaySentenceWS {
	/**
	 * 分页获取有效的每日一句信息
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public PageBeanResponse findSentence(Request req, String page, String pageSize);
	/**
	 * 获取指定句子信息
	 * @param req
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public DaySentenceResp getSentence(DaySentenceReq req);
	/**
	 * 获取下一条句子信息
	 * @param req
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public DaySentenceResp nextSentence(DaySentenceReq req);
	/**
	 * 获取上一条句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp preSentence(DaySentenceReq req);
}
